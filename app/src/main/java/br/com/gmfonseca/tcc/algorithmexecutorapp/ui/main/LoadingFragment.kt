package br.com.gmfonseca.tcc.algorithmexecutorapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.gmfonseca.tcc.algorithmexecutorapp.R
import kotlinx.android.synthetic.main.loading_fragment.*

class LoadingFragment : Fragment() {

    companion object {
        fun newInstance() = LoadingFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.loading_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = defaultViewModelProviderFactory.create(MainViewModel::class.java)

        button_cancel.setOnClickListener {
            if (savedInstanceState == null) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commit()
            }
        }

        viewModel.loadData().observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            if (it == "DONE") {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commit()
            }
        })
    }

}