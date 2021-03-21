package br.com.gmfonseca.tcc.algorithmexecutorapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.dto.SortDataDto
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.*
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.service.grpc.AlgorithmExecutorServiceGrpc
import br.com.gmfonseca.tcc.algorithmexecutorapp.business.service.rest.AlgorithmExecutorServiceRest
import br.com.gmfonseca.tcc.algorithms.BubbleSortAlgorithm
import br.com.gmfonseca.tcc.algorithms.HeapSortAlgorithm
import br.com.gmfonseca.tcc.algorithms.SelectionSortAlgorithm
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())
    private val restService = AlgorithmExecutorServiceRest.INSTANCE
    private val gson = Gson()

    var algorithm = Algorithm.BUBBLE
    var method = Method.LOCAL
    var dataAmount = 10_000
    var dataType = DataType.INTEGER

    var case = Case.BEST
    private val factor: Int; get() = if (case == Case.BEST) 0 else dataAmount

    var algorithmId: Int? = null
    var methodId: Int? = null
    var dataAmountId: Int? = null
    var dataTypeId: Int? = null
    var caseId: Int? = null

    var executionCount: Int = 0; private set
    var initialBatteryPercent: Float = -1.0f
        private set(value) {
            // Change initial battery value only for first execution
            if (executionCount == 0) field = value
        }

    private var startTime = 0L
        set(value) {
            field = if (field == 0L) {
                value
            } else {
                (startTime + value) / 2
            }
        }
    val ms; get() = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)

    private val _executionStatus = MutableLiveData<Boolean>(null)
    val executionStatus: LiveData<Boolean>; get() = _executionStatus

    fun clear() {
        executionCount = 0
        initialBatteryPercent = -1f
        startTime = 0
    }

    fun dispatch(batteryPercent: Float): LiveData<Boolean> {
        initialBatteryPercent = batteryPercent
        executionCount++

        ioScope.launch {
            try {
                val data = when (dataType) {
                    DataType.INTEGER -> MutableList(dataAmount) { abs(factor - it) }
                    DataType.FLOAT -> MutableList(dataAmount) { abs(factor - it).toFloat() }
                    DataType.OBJECT -> MutableList(dataAmount) {
                        val i = abs(factor - it)
                        AnComparableObject(UUID.randomUUID(), i, i.toFloat())
                    }
                }

                startTime = System.nanoTime()

                when (method) {
                    Method.LOCAL -> runLocal(data, _executionStatus)
                    Method.REST -> runRest(data, dataType, _executionStatus)
                    Method.GRPC -> runGRpc(data, _executionStatus)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                _executionStatus.postValue(false)
            }
        }

        return _executionStatus
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Comparable<*>> runLocal(
        items: MutableList<T>,
        liveData: MutableLiveData<Boolean>
    ) {
        when (this.algorithm) {
            Algorithm.BUBBLE -> {
                when (dataType) {
                    DataType.INTEGER -> BubbleSortAlgorithm<Int>().sort(items as MutableList<Int>)
                    DataType.FLOAT -> BubbleSortAlgorithm<Float>().sort(items as MutableList<Float>)
                    DataType.OBJECT -> BubbleSortAlgorithm<AnComparableObject>().sort(items as MutableList<AnComparableObject>)
                }
            }
            Algorithm.HEAP -> {
                when (dataType) {
                    DataType.INTEGER -> HeapSortAlgorithm<Int>().sort(items as MutableList<Int>)
                    DataType.FLOAT -> HeapSortAlgorithm<Float>().sort(items as MutableList<Float>)
                    DataType.OBJECT -> HeapSortAlgorithm<AnComparableObject>().sort(items as MutableList<AnComparableObject>)
                }
            }
            Algorithm.SELECTION -> {
                when (dataType) {
                    DataType.INTEGER -> SelectionSortAlgorithm<Int>().sort(items as MutableList<Int>)
                    DataType.FLOAT -> SelectionSortAlgorithm<Float>().sort(items as MutableList<Float>)
                    DataType.OBJECT -> SelectionSortAlgorithm<AnComparableObject>().sort(items as MutableList<AnComparableObject>)
                }
            }
        }

        liveData.postValue(true)
    }

    private fun <T : Any> runRest(
        items: List<T>,
        type: DataType,
        liveData: MutableLiveData<Boolean>
    ) {
        val result = when (algorithm) {
            Algorithm.BUBBLE -> restService.executeBubbleSort(
                body = RequestBody.create(
                    MediaType.parse("application/json"),
                    gson.toJson(SortDataDto(type = type, elements = items))
                )
            )
            Algorithm.HEAP -> restService.executeHeapSort(
                body = RequestBody.create(
                    MediaType.parse("application/json"),
                    gson.toJson(SortDataDto(type = type, elements = items))
                )
            )
            Algorithm.SELECTION -> restService.executeSelectionSort(
                body = RequestBody.create(
                    MediaType.parse("application/json"),
                    gson.toJson(SortDataDto(type = type, elements = items))
                )
            )
        }.execute().body()

        liveData.postValue(result != null)
    }

    private fun <T : Any> runGRpc(
        items: List<T>,
        liveData: MutableLiveData<Boolean>
    ) {
        val result = when (this.algorithm) {
            Algorithm.BUBBLE -> AlgorithmExecutorServiceGrpc.executeBubbleSortAlgorithm(items)
            Algorithm.HEAP -> AlgorithmExecutorServiceGrpc.executeHeapSortAlgorithm(items)
            Algorithm.SELECTION -> AlgorithmExecutorServiceGrpc.executeSelectionSortAlgorithm(items)
        }

        liveData.postValue(result != null)
    }
}
