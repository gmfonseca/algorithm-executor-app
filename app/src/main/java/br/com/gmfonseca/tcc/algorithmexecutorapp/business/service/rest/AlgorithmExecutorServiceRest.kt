package br.com.gmfonseca.tcc.algorithmexecutorapp.business.service.rest

import br.com.gmfonseca.tcc.algorithmexecutorapp.shared.Constants
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface AlgorithmExecutorServiceRest {

    @POST("execute/bubble-sort")
    fun executeBubbleSort(
        @Body body: RequestBody
    ): Call<ResponseBody>

    @POST("execute/selection-sort")
    fun executeSelectionSort(
        @Body body: RequestBody
    ): Call<ResponseBody>

    @POST("execute/heap-sort")
    fun executeHeapSort(
        @Body body: RequestBody
    ): Call<ResponseBody>

    companion object {
        val INSTANCE: AlgorithmExecutorServiceRest by lazy {
            val client = OkHttpClient.Builder()
                    .readTimeout(5, TimeUnit.MINUTES)
                    .callTimeout(5, TimeUnit.MINUTES)
                    .connectTimeout(5, TimeUnit.MINUTES)
                .build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(Constants.REST_SERVER)
                .client(client)
                .build()

            retrofit.create(AlgorithmExecutorServiceRest::class.java)
        }
    }
}