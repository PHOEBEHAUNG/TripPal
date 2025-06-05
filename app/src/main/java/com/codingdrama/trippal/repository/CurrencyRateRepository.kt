package com.codingdrama.trippal.repository

import com.codingdrama.trippal.data.CurrencyEnum
import com.codingdrama.trippal.model.network.ApiClient
import com.codingdrama.trippal.model.network.interfaces.CurrencyDetailsResponse
import com.codingdrama.trippal.model.network.interfaces.CurrencyResponse

class CurrencyRateRepository(private val apiClient: ApiClient) {
    suspend fun getSupportedCurrencies(): CurrencyDetailsResponse? {
        val response = apiClient.currencyApiService.getSupportedCurrencies()
        return response
    }

    suspend fun getAllLatestCurrencyRates(base: String): CurrencyResponse? {
        // a list string like USD,EUR,GBP,JPY from all CurrencyEnum
        val currencies = CurrencyEnum.entries.toTypedArray().joinToString(",") { it.name }
        val response = apiClient.currencyApiService.getLatestRate(base, currencies)
        return response
    }

    suspend fun getLatestCurrencyRates(base: String, currency: String): CurrencyResponse? {
        val response = apiClient.currencyApiService.getLatestRate(base, currency)
        return response
    }
}