package br.com.gmfonseca.tcc.algorithmexecutorapp.ui.main

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.gmfonseca.tcc.algorithmexecutorapp.R
import kotlinx.android.synthetic.main.loading_fragment.*


class LoadingFragment : Fragment(R.layout.loading_fragment) {
    private val batteryStatus: Intent?
        get() = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { iFilter ->
            requireContext().registerReceiver(null, iFilter)
        }

    private val batteryPercent: Float
        get() = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level / scale.toFloat()
        } ?: -1f

    private val batteryMilliAmp; get() = getBatteryCapacity() * batteryPercent

    private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        resultSection.isVisible = false
        loadingBar.isVisible = true
        button_back.isVisible = false
        textView_chosenAlgorithm.text = viewModel.algorithm.name
        textView_chosenMethod.text = viewModel.method.name
        textView_chosenDataAmount.text = viewModel.dataAmount.toString()
        textView_chosenDataType.text = viewModel.dataType.name
        textView_chosenCase.text = viewModel.case.name

        viewModel.dispatch(batteryMilliAmp, batteryPercent).observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            if (it) {
                handleResult()
            } else {
                onFinish()
            }
        })

        button_back.setOnClickListener { onFinish() }
    }

    private fun handleResult() {
        val batteryResult = viewModel.startBattery - batteryMilliAmp
        val batteryPercentResult = viewModel.startBatteryPercent - batteryPercent
        textView_resultBatteryPercent.text = getString(R.string.loading_result_battery_percent, batteryPercentResult)
        textView_resultBattery.text = getString(R.string.loading_result_battery, batteryResult)
        textView_resultSpentTime.text = getString(R.string.loading_result_time, viewModel.ms)
        resultSection.isVisible = true
        button_back.isVisible = true
        loadingBar.isVisible = false
    }

    private fun onFinish() {
        try {
            activity?.run {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commit()
            }
            System.gc()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    private fun getBatteryCapacity(): Double {
        val mPowerProfile: Any
        var batteryCapacity = 0.0
        val POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile"
        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                .getConstructor(Context::class.java)
                .newInstance(activity?.applicationContext)
            batteryCapacity = Class
                .forName(POWER_PROFILE_CLASS)
                .getMethod("getBatteryCapacity")
                .invoke(mPowerProfile) as Double
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return batteryCapacity
    }

    companion object {
        fun newInstance() = LoadingFragment()
    }
}
