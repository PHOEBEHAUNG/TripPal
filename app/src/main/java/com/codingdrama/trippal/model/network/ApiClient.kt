package com.codingdrama.trippal.model.network

import com.codingdrama.trippal.BuildConfig
import com.codingdrama.trippal.model.network.interfaces.CurrencyService
import com.codingdrama.trippal.model.network.interfaces.KIAApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL_KAI = "https://www.kia.gov.tw/"
    private const val BASE_URL_CURRENCY = "https://api.freecurrencyapi.com/v1/"

    val kiaApiService: KIAApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_KAI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KIAApiService::class.java)
    }

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