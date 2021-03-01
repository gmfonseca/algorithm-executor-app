package br.com.gmfonseca.tcc.algorithmexecutorapp.business.service.rest

import br.com.gmfonseca.tcc.algorithmexecutorapp.shared.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface AlgorithmExecutorServiceRest {

    @GET("/execute/heap-sort")
    fun executeHeapSort(@Field("type") type: String, @Body elements: Array<Any>): List<Any>

    @GET("/execute/bubble-sort")
    fun executeBubbleSort(@Field("type") type: String, @Body elements: Array<Any>): List<Any>

    @GET("/execute/selection-sort")
    fun executeSelectionSort(@Field("type") type: String, @Body elements: Array<Any>): List<Any>

    companion object {
        val INSTANCE: AlgorithmExecutorServiceRest
            get() {
                val client = OkHttpClient.Builder()
                    .readTimeout(5, TimeUnit.MINUTES)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.REST_SERVER)
                    .client(client)
                    .build()

                return retrofit.create(AlgorithmExecutorServiceRest::class.java)
            }
    }
}