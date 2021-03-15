package br.com.gmfonseca.tcc.algorithmexecutorapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        setupListeners()
        handleChecked()

        button_start.setOnClickListener {
            if (savedInstanceState == null) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoadingFragment.newInstance())
                    .commit()
            }
        }
    }

    private fun setupListeners() {
        radioGroup_algorithm.setOnCheckedChangeListener { _, checkedId ->
            val (algorithm, id) = when (checkedId) {
                radioButton_algorithm_heap.id -> Algorithm.HEAP to radioButton_algorithm_heap.id
                radioButton_algorithm_selection.id -> Algorithm.SELECTION to radioButton_algorithm_selection.id
                else -> Algorithm.BUBBLE to radioButton_algorithm_bubble.id
            }

            viewModel.algorithm = algorithm
            viewModel.algorithmId = id
        }

        radioGroup_method.setOnCheckedChangeListener { _, checkedId ->
            val (method, id) = when (checkedId) {
                radioButton_method_rest.id -> Method.REST to radioButton_method_rest.id
                radioButton_method_grpc.id -> Method.GRPC to radioButton_method_grpc.id
                else -> Method.LOCAL to radioButton_method_local.id
            }

            viewModel.method = method
            viewModel.methodId = id
        }

        radioGroup_dataType.setOnCheckedChangeListener { _, checkedId ->
            val (dataType, id) = when (checkedId) {
                radioButton_type_float.id -> DataType.FLOAT to radioButton_type_float.id
                radioButton_type_object.id -> DataType.OBJECT to radioButton_type_object.id
                else -> DataType.INTEGER to radioButton_type_int.id
            }

            viewModel.dataType = dataType
            viewModel.dataTypeId = id
        }

        radioGroup_case.setOnCheckedChangeListener { _, checkedId ->
            val (case, id) = when (checkedId) {
                radioButton_case_worst.id -> Case.WORST to radioButton_case_worst.id
                else -> Case.BEST to radioButton_case_best.id
            }

            viewModel.case = case
            viewModel.caseId = id
        }

        radioGroup_dataAmount.setOnCheckedChangeListener { _, checkedId ->
            val (dataAmount, id) = when (checkedId) {
                radioButton_amount_100k.id -> 100_000 to radioButton_amount_100k.id
                radioButton_amount_250k.id -> 200_000 to radioButton_amount_250k.id
                else -> 10_000 to radioButton_amount_10k.id
            }

            viewModel.dataAmount = dataAmount
            viewModel.dataAmountId = id
        }
    }

    private fun handleChecked() {
        radioGroup_algorithm.check(viewModel.algorithmId ?: radioButton_algorithm_bubble.id)
        radioGroup_method.check(viewModel.methodId ?: radioButton_method_local.id)
        radioGroup_dataAmount.check(viewModel.dataAmountId ?: radioButton_amount_10k.id)
        radioGroup_dataType.check(viewModel.dataTypeId ?: radioButton_type_int.id)
        radioGroup_case.check(viewModel.caseId ?: radioButton_case_best.id)
    }
}
