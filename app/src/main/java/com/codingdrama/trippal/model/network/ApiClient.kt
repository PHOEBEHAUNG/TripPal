package com.codingdrama.trippal.model.network

import com.codingdrama.trippal.BuildConfig
import com.codingdrama.trippal.model.network.data.InstantSchedules
import com.codingdrama.trippal.model.network.data.InstantSchedulesResponse
import com.codingdrama.trippal.model.network.interfaces.CurrencyService
import com.codingdrama.trippal.model.network.interfaces.KIAApiService
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


enum class ErrorCode(val code: Int, val message: String) {
    SUCCESS(90000, "Success"),
    UNKNOWN(90001, "Unknown error"),
    INTERNET(90002, "Internet connection issue"),
    SERVER_ISSUE(90003, "Server issue")
}

object ApiClient {
    private const val BASE_URL_KAI = "https://www.kia.gov.tw/"
    private const val BASE_URL_CURRENCY = "https://api.freecurrencyapi.com/v1/"

    //---------------------------------------------------//
    // KIA API client
    // a kia http client with Interceptor to return a error code like 404, internet problem when the request is not successful
    private val kiaApiHttpClient = okhttp3.OkHttpClient.Builder()
        .addInterceptor(KIAResponseInterceptor())
        .build()

    val kiaApiService: KIAApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_KAI)
            .client(kiaApiHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KIAApiService::class.java)
    }

    class KIAResponseInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)
            val customResponseBuilder = response.newBuilder()
            return try {
                if (response.isSuccessful) {
                    // Return a custom body response
                    val bodyString = response.body()?.string() ?: "{}"
                    val data = Gson().fromJson(bodyString, InstantSchedules::class.java)
                    val customBody = InstantSchedulesResponse(data, ErrorCode.SUCCESS.message, ErrorCode.SUCCESS.code)

                    // Convert the custom object to a JSON JSONObject
                    val responseBodyString = Gson().toJson(customBody)
                    val contentType: MediaType? = response.body()!!.contentType()
                    val body: ResponseBody = ResponseBody.create(contentType, responseBodyString)

                    customResponseBuilder
                        .request(request)
                        .code(response.code()) // or any other error code you want to simulate
                        .message(response.message())
                        .body(body)
                        .build()
                } else {
                    // Return a custom response with an error code
                    val customBody = InstantSchedulesResponse(null, ErrorCode.SERVER_ISSUE.message, ErrorCode.SERVER_ISSUE.code)
                    // Convert the custom object to a JSON JSONObject
                    val responseBodyString = Gson().toJson(customBody)
                    val contentType: MediaType? = response.body()!!.contentType()
                    val body: ResponseBody = ResponseBody.create(contentType, responseBodyString)

                    customResponseBuilder
                        .request(request)
                        .code(response.code()) // or any other error code you want to simulate
                        .message(response.message())
                        .body(body)
                        .build()
                }
            } catch (e: Exception) {
                // Handle the exception and return a custom response
                val customBody = InstantSchedulesResponse(null, ErrorCode.UNKNOWN.message, ErrorCode.UNKNOWN.code)
                // Convert the custom object to a JSON JSONObject
                val responseBodyString = Gson().toJson(customBody)
                val contentType: MediaType? = response.body()!!.contentType()
                val body: ResponseBody = ResponseBody.create(contentType, responseBodyString)

                response.newBuilder()
                    .request(request)
                    .code(200)
                    .message(e.message ?: ErrorCode.UNKNOWN.message)
                    .body(body)
                    .build()
            } catch (e: IOException) {
                // Handle the exception and return a custom response
                val customBody = InstantSchedulesResponse(null, ErrorCode.UNKNOWN.message, ErrorCode.UNKNOWN.code)
                // Convert the custom object to a JSON JSONObject
                val responseBodyString = Gson().toJson(customBody)
                val contentType: MediaType? = response.body()!!.contentType()
                val body: ResponseBody = ResponseBody.create(contentType, responseBodyString)

                response.newBuilder()
                    .request(request)
                    .code(200)
                    .message(e.message ?: ErrorCode.UNKNOWN.message)
                    .body(body)
                    .build()
            }
        }
    }

    //---------------------------------------------------//
    // Currency API client
    // a Interceptor of http client to add the API key
    private val currencyApiInterceptor = { chain: okhttp3.Interceptor.Chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("apikey", BuildConfig.CURRENCY_API_KEY)
            .build()
        chain.proceed(newRequest)
    }

    private val currencyApiHttpClient = okhttp3.OkHttpClient.Builder()
        .addInterceptor(currencyApiInterceptor)
        .build()

    val currencyApiService: CurrencyService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_CURRENCY)
            .addConverterFactory(GsonConverterFactory.create())
            .client(currencyApiHttpClient)
            .build()
            .create(CurrencyService::class.java)
    }
}