package com.codingdrama.trippal.repository

import com.codingdrama.trippal.model.network.ApiClient
import com.codingdrama.trippal.model.network.data.InstantSchedules

class FlightInfoRepository (private val apiClient: ApiClient) {
    suspend fun getFlightInfoArrive(): InstantSchedules {
        val domestic = apiClient.kiaApiService.getFlightInfoDomesticArrive()
        val international = apiClient.kiaApiService.getFlightInfoInternationalArrive()

        // merge the two lists into an InstantSchedule list
        val domesticList = domestic.instantSchedules
        val internationalList = international.instantSchedules
        val result = domesticList + internationalList
        result.sortedBy { it.expectTime }
        return InstantSchedules(result)
    }

    suspend fun getFlightInfoDeparture(): InstantSchedules {
        val domestic = apiClient.kiaApiService.getFlightInfoDomesticDeparture()
        val international = apiClient.kiaApiService.getFlightInfoInternationalDeparture()

        // merge the two lists into an InstantSchedule list
        val domesticList = domestic.instantSchedules
        val internationalList = international.instantSchedules
        val result = domesticList + internationalList
        result.sortedBy { it.expectTime }
        return InstantSchedules(result)
    }
}