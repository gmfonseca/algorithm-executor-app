package br.com.gmfonseca.tcc.algorithmexecutorapp.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.gmfonseca.tcc.algorithmexecutorapp.R
import kotlinx.android.synthetic.main.loading_fragment.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class LoadingFragment : Fragment(R.layout.loading_fragment), Callback {

    companion object {
        fun newInstance() = LoadingFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        textView_chosenAlgorithm.text = viewModel.algorithm.name
        textView_chosenMethod.text = viewModel.method.name
        textView_chosenDataAmount.text = viewModel.dataAmount.toString()
        textView_chosenDataType.text = viewModel.dataType.name
        textView_chosenCase.text = viewModel.case.name

        viewModel.dispatch(this).observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            if (it == "FAIL") {
                onFinish("Failed to run algorithms without throwable")
            } else {
                onFinish("Not fail")
            }
        })
    }

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        if (response.isSuccessful) {
            onFinish("Successfully run algorithms")
        } else {
            onFinish("Failed running algorithms, code: ${response.code()}")
        }
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        t.printStackTrace()
        onFinish("Failed to run algorithms, $t")
    }

    private fun onFinish(msg: String) {
        try {
            val time = viewModel.seconds
            activity?.let {
                Toast.makeText(it.applicationContext, "Spent $time ms - $msg", Toast.LENGTH_LONG)
                    .show()
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commit()
            }
            System.gc()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    override fun onFinish(success: Boolean) {
        if (success) {
            onFinish("Succeed to run")
        } else {
            onFinish("Failed to run reported")
        }
    }
}

interface Callback : retrofit2.Callback<ResponseBody> {
    fun onFinish(success: Boolean)
}