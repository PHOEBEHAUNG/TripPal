package com.codingdrama.trippal.model.network

import com.codingdrama.trippal.model.network.data.InstantSchedules
import com.codingdrama.trippal.model.network.data.InstantSchedulesResponse
import com.codingdrama.trippal.model.network.interfaces.KIAApiService
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object KiaApiClient {
    private const val BASE_URL_KAI = "https://www.kia.gov.tw/"

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

            var response: Response? = null
            try {
                response = chain.proceed(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (response == null) {
                // If the response is null, return a custom error response
                val customBody = InstantSchedulesResponse(null, ErrorCode.INTERNET.message, ErrorCode.INTERNET.code)
                // Convert the custom object to a JSON JSONObject
                val responseBodyString = Gson().toJson(customBody)
                val contentType: MediaType? = MediaType.get("application/json")
                val body: ResponseBody = ResponseBody.create(contentType, responseBodyString)

                return Response.Builder()
                    .request(request)
                    .code(200)
                    .message(ErrorCode.INTERNET.message)
                    .body(body)
                    .build()
            }

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
                        .code(200) // or any other error code you want to simulate
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
}