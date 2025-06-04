package com.codingdrama.trippal.model.network

import com.codingdrama.trippal.model.network.interfaces.KIAApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://www.kia.gov.tw/"

    val kiaApiService: KIAApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KIAApiService::class.java)
    }
}