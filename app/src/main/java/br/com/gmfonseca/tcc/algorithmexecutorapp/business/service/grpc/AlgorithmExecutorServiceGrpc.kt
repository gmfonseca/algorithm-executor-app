package br.com.gmfonseca.tcc.algorithmexecutorapp.business.service.grpc

import br.com.gmfonseca.tcc.algorithmexecutorapp.business.model.AnComparableObject
import br.com.gmfonseca.tcc.algorithmexecutorapp.shared.Constants
import br.com.gmfonseca.tcc.algorithmexecutorapp.toAnObjectList
import br.com.gmfonseca.tcc.proto.AlgorithmExecutor
import br.com.gmfonseca.tcc.proto.AlgorithmExecutorServiceGrpc
import io.grpc.ManagedChannelBuilder

@Suppress("UNCHECKED_CAST")
object AlgorithmExecutorServiceGrpc {

    fun executeBubbleSortAlgorithm(data: List<Any>): List<Any>? {
        return try {
            val service = AlgorithmExecutorServiceGrpc.newBlockingStub(channel)

            val requestBuilder = AlgorithmExecutor.ExecuteSortAlgorithmRequest
                .newBuilder()

            when (data.first()) {
                is Int -> {
                    requestBuilder.integerList = AlgorithmExecutor.IntegerList
                        .newBuilder().addAllContent(data as List<Int>).build()
                }
                is Float -> {
                    requestBuilder.floatList = AlgorithmExecutor.FloatList
                        .newBuilder().addAllContent(data as List<Float>).build()
                }
                is AnComparableObject -> {
                    requestBuilder.objectList = AlgorithmExecutor.ObjectList
                        .newBuilder()
                        .addAllContent((data as List<AnComparableObject>).toAnObjectList()).build()
                }
                else -> throw IllegalArgumentException("Invalid data type")
            }

            val response = service.executeBubbleSortAlgorithm(requestBuilder.build())

            when (response.dataCase) {
                AlgorithmExecutor.ExecuteAlgorithmResult.DataCase.INTEGERLIST -> response.integerList.contentList
                AlgorithmExecutor.ExecuteAlgorithmResult.DataCase.FLOATLIST -> response.floatList.contentList
                AlgorithmExecutor.ExecuteAlgorithmResult.DataCase.OBJECTLIST -> response.objectList.contentList
                else -> null
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            null
        } finally {
            if (!channel.isShutdown) {
                channel.shutdown()
            }
        }
    }

    fun executeHeapSortAlgorithm(data: List<Any>): List<Any>? {
        return try {
            val service = AlgorithmExecutorServiceGrpc.newBlockingStub(channel)

            val requestBuilder = AlgorithmExecutor.ExecuteSortAlgorithmRequest
                .newBuilder()

            when (data.first()) {
                is Int -> {
                    requestBuilder.integerList = AlgorithmExecutor.IntegerList
                        .newBuilder().addAllContent(data as List<Int>).build()
                }
                is Float -> {
                    requestBuilder.floatList = AlgorithmExecutor.FloatList
                        .newBuilder().addAllContent(data as List<Float>).build()
                }
                is AnComparableObject -> {
                    requestBuilder.objectList = AlgorithmExecutor.ObjectList
                        .newBuilder()
                        .addAllContent((data as List<AnComparableObject>).toAnObjectList()).build()
                }
                else -> throw IllegalArgumentException("Invalid data type")
            }

            val response = service.executeHeapSortAlgorithm(requestBuilder.build())

            when (response.dataCase) {
                AlgorithmExecutor.ExecuteAlgorithmResult.DataCase.INTEGERLIST -> response.integerList.contentList
                AlgorithmExecutor.ExecuteAlgorithmResult.DataCase.FLOATLIST -> response.floatList.contentList
                AlgorithmExecutor.ExecuteAlgorithmResult.DataCase.OBJECTLIST -> response.objectList.contentList
                else -> null
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            null
        } finally {
            if (!channel.isShutdown) {
                channel.shutdown()
            }
        }
    }

    fun executeSelectionSortAlgorithm(data: List<Any>): List<Any>? {
        return try {
            val service = AlgorithmExecutorServiceGrpc.newBlockingStub(channel)

            val requestBuilder = AlgorithmExecutor.ExecuteSortAlgorithmRequest
                .newBuilder()

            when (data.first()) {
                is Int -> {
                    requestBuilder.integerList = AlgorithmExecutor.IntegerList
                        .newBuilder().addAllContent(data as List<Int>).build()
                }
                is Float -> {
                    requestBuilder.floatList = AlgorithmExecutor.FloatList
                        .newBuilder().addAllContent(data as List<Float>).build()
                }
                is AnComparableObject -> {
                    requestBuilder.objectList = AlgorithmExecutor.ObjectList
                        .newBuilder()
                        .addAllContent((data as List<AnComparableObject>).toAnObjectList()).build()
                }
                else -> throw IllegalArgumentException("Invalid data type")
            }

            val response = service.executeSelectionSortAlgorithm(requestBuilder.build())

            when (response.dataCase) {
                AlgorithmExecutor.ExecuteAlgorithmResult.DataCase.INTEGERLIST -> response.integerList.contentList
                AlgorithmExecutor.ExecuteAlgorithmResult.DataCase.FLOATLIST -> response.floatList.contentList
                AlgorithmExecutor.ExecuteAlgorithmResult.DataCase.OBJECTLIST -> response.objectList.contentList
                else -> null
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            null
        } finally {
            if (!channel.isShutdown) {
                channel.shutdown()
            }
        }
    }

    private val channel = ManagedChannelBuilder
        .forTarget(Constants.GRPC_SERVER)
        .usePlaintext()
        .build()

}