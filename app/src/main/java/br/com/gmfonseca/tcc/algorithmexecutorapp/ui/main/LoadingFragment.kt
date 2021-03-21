package br.com.gmfonseca.tcc.algorithmexecutorapp.ui.main

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

        viewModel.executionStatus.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            if (it) {
                handleResult()
            } else {
                onFinish()
            }
        })

        button_back.setOnClickListener { onFinish() }
        startExecution()
    }

    private fun handleResult() {
        val batteryPercentResult = (viewModel.initialBatteryPercent - batteryPercent) / viewModel.executionCount

        if (batteryPercentResult != 0.0f) { // Check if it consumed enough battery
            textView_resultExecutionCount.text =
                getString(R.string.loading_result_number_executions, viewModel.executionCount)
            textView_resultBatteryPercent.text =
                getString(R.string.loading_result_battery_percent, batteryPercentResult)
            textView_resultSpentTime.text = getString(R.string.loading_result_time, viewModel.ms)
            resultSection.isVisible = true
            button_back.isVisible = true
            loadingBar.isVisible = false
        } else {
            startExecution()
        }
    }

    private fun startExecution() {
        viewModel.dispatch(batteryPercent)
    }

    private fun onFinish() {
        try {
            viewModel.clear()
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

    companion object {
        fun newInstance() = LoadingFragment()
    }
}
