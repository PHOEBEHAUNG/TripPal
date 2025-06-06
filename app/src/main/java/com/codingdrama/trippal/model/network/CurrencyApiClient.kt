package com.codingdrama.trippal.model.network

import com.codingdrama.trippal.BuildConfig
import com.codingdrama.trippal.model.network.interfaces.CurrencyService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CurrencyApiClient {
    private const val BASE_URL_CURRENCY = "https://api.freecurrencyapi.com/v1/"

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