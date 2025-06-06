package com.codingdrama.trippal.repository

import com.codingdrama.trippal.model.network.ApiClient
import com.codingdrama.trippal.model.network.ErrorCode
import com.codingdrama.trippal.model.network.data.InstantSchedules

class FlightInfoRepository (private val apiClient: ApiClient) {
    suspend fun getFlightInfoArrive(): InstantSchedules {
        val domestic = apiClient.kiaApiService.getFlightInfoDomesticArrive()
        val international = apiClient.kiaApiService.getFlightInfoInternationalArrive()

        if (domestic.errorCode == ErrorCode.SUCCESS.code && international.errorCode == ErrorCode.SUCCESS.code) {
            // merge the two lists into an InstantSchedule list
            val domesticList = domestic.data?.instantSchedules!!
            val internationalList = international.data?.instantSchedules!!
            val result = domesticList + internationalList
            result.sortedBy { it.expectTime }
            return InstantSchedules(result)
        } else if (domestic.errorCode == ErrorCode.SUCCESS.code) {
            return InstantSchedules(domestic.data?.instantSchedules?.sortedBy { it.expectTime } ?: emptyList())
        } else if (international.errorCode == ErrorCode.SUCCESS.code) {
            return InstantSchedules(domestic.data?.instantSchedules?.sortedBy { it.expectTime } ?: emptyList())
        } else {
            return InstantSchedules(emptyList())
        }
    }

    suspend fun getFlightInfoDeparture(): InstantSchedules {
        val domestic = apiClient.kiaApiService.getFlightInfoDomesticDeparture()
        val international = apiClient.kiaApiService.getFlightInfoInternationalDeparture()

        if (domestic.errorCode == ErrorCode.SUCCESS.code && international.errorCode == ErrorCode.SUCCESS.code) {
            // merge the two lists into an InstantSchedule list
            val domesticList = domestic.data?.instantSchedules!!
            val internationalList = international.data?.instantSchedules!!
            val result = domesticList + internationalList
            result.sortedBy { it.expectTime }
            return InstantSchedules(result)
        } else if (domestic.errorCode == ErrorCode.SUCCESS.code) {
            return InstantSchedules(domestic.data?.instantSchedules?.sortedBy { it.expectTime } ?: emptyList())
        } else if (international.errorCode == ErrorCode.SUCCESS.code) {
            return InstantSchedules(domestic.data?.instantSchedules?.sortedBy { it.expectTime } ?: emptyList())
        } else {
            return InstantSchedules(emptyList())
        }
    }
}