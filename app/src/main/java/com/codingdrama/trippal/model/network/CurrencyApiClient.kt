package com.codingdrama.trippal.model.network

import com.codingdrama.trippal.BuildConfig
import com.codingdrama.trippal.model.network.data.CurrencyDataResponse
import com.codingdrama.trippal.model.network.interfaces.CurrencyService
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object CurrencyApiClient {
    private const val BASE_URL_CURRENCY = "https://api.freecurrencyapi.com/v1/"

    // a Interceptor of http client to add the API key
    class CurrencyApiResponseInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("apikey", BuildConfig.CURRENCY_API_KEY)
                .build()

            var response: Response? = null
            try {
                response = chain.proceed(newRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (response == null) {
                // If the response is null, return a custom error response
                val customBody = CurrencyDataResponse(null, ErrorCode.INTERNET.message, ErrorCode.INTERNET.code)
                // Convert the custom object to a JSON JSONObject
                val responseBodyString = Gson().toJson(customBody)
                val contentType: MediaType? = MediaType.get("application/json")
                val body: ResponseBody = ResponseBody.create(contentType, responseBodyString)

                return Response.Builder()
                    .request(newRequest)
                    .code(200)
                    .message(ErrorCode.INTERNET.message)
                    .protocol(okhttp3.Protocol.HTTP_1_1)
                    .body(body)
                    .build()
            }

            val customResponseBuilder = response.newBuilder()
            return try {
                val bodyString = response.body()?.string() ?: "{}"
                if (response.isSuccessful && !bodyString.contains("errors")) {
                    // Return a custom body response
                    val customBody = CurrencyDataResponse(bodyString, ErrorCode.SUCCESS.message, ErrorCode.SUCCESS.code)
                    // Convert the custom object to a JSON JSONObject
                    val responseBodyString = Gson().toJson(customBody)
                    val contentType: MediaType? = response.body()!!.contentType()
                    val body: ResponseBody = ResponseBody.create(contentType, responseBodyString)

                    customResponseBuilder
                        .request(newRequest)
                        .code(response.code()) // or any other error code you want to simulate
                        .message(response.message())
                        .body(body)
                        .build()
                } else {
                    // Return a custom response with an error code
                    val customBody = CurrencyDataResponse(null, ErrorCode.SERVER_ISSUE.message, ErrorCode.SERVER_ISSUE.code)
                    // Convert the custom object to a JSON JSONObject
                    val responseBodyString = Gson().toJson(customBody)
                    val contentType: MediaType? = response.body()!!.contentType()
                    val body: ResponseBody = ResponseBody.create(contentType, responseBodyString)

                    customResponseBuilder
                        .request(newRequest)
                        .code(200) // or any other error code you want to simulate
                        .message(response.message())
                        .body(body)
                        .build()
                }
            } catch (e: Exception) {
                // Handle the exception and return a custom response
                val customBody = CurrencyDataResponse(null, ErrorCode.UNKNOWN.message, ErrorCode.UNKNOWN.code)
                // Convert the custom object to a JSON JSONObject
                val responseBodyString = Gson().toJson(customBody)
                val contentType: MediaType? = response.body()!!.contentType()
                val body: ResponseBody = ResponseBody.create(contentType, responseBodyString)

                response.newBuilder()
                    .request(newRequest)
                    .code(200)
                    .message(e.message ?: ErrorCode.UNKNOWN.message)
                    .body(body)
                    .build()
            } catch (e: IOException) {
                // Handle the exception and return a custom response
                val customBody = CurrencyDataResponse(null, ErrorCode.UNKNOWN.message, ErrorCode.UNKNOWN.code)
                // Convert the custom object to a JSON JSONObject
                val responseBodyString = Gson().toJson(customBody)
                val contentType: MediaType? = response.body()!!.contentType()
                val body: ResponseBody = ResponseBody.create(contentType, responseBodyString)

                response.newBuilder()
                    .request(newRequest)
                    .code(200)
                    .message(e.message ?: ErrorCode.UNKNOWN.message)
                    .body(body)
                    .build()
            }
        }
    }

    private val currencyApiHttpClient = okhttp3.OkHttpClient.Builder()
        .addInterceptor(CurrencyApiResponseInterceptor())
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