package com.codingdrama.trippal.repository

import com.codingdrama.trippal.data.CurrencyEnum
import com.codingdrama.trippal.model.network.CurrencyApiClient
import com.codingdrama.trippal.model.network.data.CurrencyDetailsResponse
import com.codingdrama.trippal.model.network.data.CurrencyResponse
import com.google.gson.Gson
import javax.inject.Inject

class CurrencyRateRepository @Inject constructor(private val apiClient: CurrencyApiClient) {
    suspend fun getSupportedCurrencies(): CurrencyDetailsResponse? {
        val response = apiClient.currencyApiService.getSupportedCurrencies()
        if (response?.data != null) {
            // covert response.data to CurrencyDetailsResponse
            return Gson().fromJson(response.data, CurrencyDetailsResponse::class.java)
        } else {
            return null
        }
    }

    suspend fun getAllLatestCurrencyRates(base: String): CurrencyResponse? {
        // a list string like USD,EUR,GBP,JPY from all CurrencyEnum
        val currencies = CurrencyEnum.entries.toTypedArray().joinToString(",") { it.name }
        val response = apiClient.currencyApiService.getLatestRate(base, currencies)
        if (response?.data != null) {
            // covert response.data to CurrencyResponse
            return Gson().fromJson(response.data, CurrencyResponse::class.java)
        } else {
            return null
        }
    }

    suspend fun getLatestCurrencyRates(base: String, currency: String): CurrencyResponse? {
        val response = apiClient.currencyApiService.getLatestRate(base, currency)
        if (response?.data != null) {
            // covert response.data to CurrencyResponse
            return Gson().fromJson(response.data, CurrencyResponse::class.java)
        } else {
            return null
        }
    }
}