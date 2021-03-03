package br.com.gmfonseca.tcc.algorithmexecutorapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.gmfonseca.tcc.algorithmexecutorapp.R
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.Algorithm
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.Case
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.DataType
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.Method
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = defaultViewModelProviderFactory.create(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_start.setOnClickListener {
            if (savedInstanceState == null) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoadingFragment.newInstance())
                    .commit()
            }
        }

        radioGroup_algorithm.setOnCheckedChangeListener { _, checkedId ->
            viewModel.algorithm = when (checkedId) {
                radioButton_algorithm_heap.id -> Algorithm.BUBBLE
                radioButton_algorithm_selection.id -> Algorithm.BUBBLE
                else -> Algorithm.BUBBLE
            }
        }

        radioGroup_method.setOnCheckedChangeListener { _, checkedId ->
            viewModel.method = when (checkedId) {
                radioButton_method_rest.id -> Method.REST
                radioButton_method_grpc.id -> Method.GRPC
                else -> Method.LOCAL
            }
        }

        radioGroup_dataType.setOnCheckedChangeListener { _, checkedId ->
            viewModel.dataType = when (checkedId) {
                radioButton_type_float.id -> DataType.FLOAT
                radioButton_type_object.id -> DataType.OBJECT
                else -> DataType.INTEGER
            }
        }

        radioGroup_case.setOnCheckedChangeListener { _, checkedId ->
            viewModel.case = when (checkedId) {
                radioButton_case_best.id -> Case.WORST
                else -> Case.BEST
            }
        }

        radioGroup_dataAmount.setOnCheckedChangeListener { _, checkedId ->
            viewModel.dataAmount = when (checkedId) {
                radioButton_amount_100k.id -> 100_000
                radioButton_amount_1m.id -> 1_000_000
                else -> 10_000
            }
        }
    }
}
