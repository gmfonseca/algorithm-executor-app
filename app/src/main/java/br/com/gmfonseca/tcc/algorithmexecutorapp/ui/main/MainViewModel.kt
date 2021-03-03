package br.com.gmfonseca.tcc.algorithmexecutorapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.Database
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.*
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.service.grpc.AlgorithmExecutorServiceGrpc
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.service.rest.AlgorithmExecutorServiceRest
import br.com.gmfonseca.tcc.algorithms.BubbleSortAlgorithm
import br.com.gmfonseca.tcc.algorithms.HeapSortAlgorithm
import br.com.gmfonseca.tcc.algorithms.SelectionSortAlgorithm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    var algorithm = Algorithm.BUBBLE
    var method = Method.LOCAL
    var dataAmount = 10_000
    var dataType = DataType.INTEGER
    var case = Case.BEST
    var cancel = false

    fun dispatch(): LiveData<String?> {
        val mutableLiveData = MutableLiveData<String?>(null)
        cancel = false

        // something wrong here
        ioScope.launch {
            try {
                val db = Database.getInstance(getApplication<Application>().applicationContext)

                val data = when (dataType) {
                    DataType.INTEGER -> db.integerDataDao().getDataByCount(count = dataAmount)?.content
                    DataType.FLOAT -> db.floatDataDao().getDataByCount(count = dataAmount)?.content
                    DataType.OBJECT -> db.integerDataDao().getDataByCount(count = dataAmount)?.run {
                        content.map {
                            AnComparableObject(
                                UUID.randomUUID(),
                                it,
                                it.toFloat()
                            )
                        }
                    }
                }

                data?.let {
                    when (method) {
                        Method.LOCAL -> runLocal(data)
                        Method.REST -> runRest(it, dataType)
                        Method.GRPC -> runGrpc(it, dataType)
                    }
                }

                mutableLiveData.postValue("DONE")
            } catch (t: Throwable) {
                t.printStackTrace()
                mutableLiveData.postValue("FAIL")
            }
        }

        return mutableLiveData
    }

    // Todo: Refactor this method
    private fun <T : Comparable<*>> runLocal(items: List<T>) {
        when (this.algorithm) {
            Algorithm.BUBBLE -> {
                when (dataType) {
                    DataType.INTEGER -> BubbleSortAlgorithm<Int>().sort(items as List<Int>)
                    DataType.FLOAT -> BubbleSortAlgorithm<Float>().sort(items as List<Float>)
                    DataType.OBJECT -> BubbleSortAlgorithm<AnComparableObject>().sort(items as List<AnComparableObject>)
                }
            }
            Algorithm.HEAP -> {
                when (dataType) {
                    DataType.INTEGER -> HeapSortAlgorithm<Int>().sort(items as List<Int>)
                    DataType.FLOAT -> HeapSortAlgorithm<Float>().sort(items as List<Float>)
                    DataType.OBJECT -> HeapSortAlgorithm<AnComparableObject>().sort(items as List<AnComparableObject>)
                }
            }
            Algorithm.SELECTION -> {
                when (dataType) {
                    DataType.INTEGER -> SelectionSortAlgorithm<Int>().sort(items as List<Int>)
                    DataType.FLOAT -> SelectionSortAlgorithm<Float>().sort(items as List<Float>)
                    DataType.OBJECT -> SelectionSortAlgorithm<AnComparableObject>().sort(items as List<AnComparableObject>)
                }
            }
        }
    }

    private fun <T : Comparable<*>> runRest(items: List<T>, type: DataType) {
        val rest = AlgorithmExecutorServiceRest.INSTANCE
        when (this.algorithm) {
            Algorithm.BUBBLE -> rest.executeBubbleSort(
                type = type.name,
                elements = items.toTypedArray()
            )
            Algorithm.HEAP -> rest.executeHeapSort(
                type = type.name,
                elements = items.toTypedArray()
            )
            Algorithm.SELECTION -> rest.executeSelectionSort(
                type = type.name,
                elements = items.toTypedArray()
            )
        }
    }

    private fun <T : Any> runGrpc(items: List<T>, type: DataType) {
        when (this.algorithm) {
            Algorithm.BUBBLE -> AlgorithmExecutorServiceGrpc.executeBubbleSortAlgorithm(items)
            Algorithm.HEAP -> AlgorithmExecutorServiceGrpc.executeHeapSortAlgorithm(items)
            Algorithm.SELECTION -> AlgorithmExecutorServiceGrpc.executeSelectionSortAlgorithm(items)
        }
    }
}
