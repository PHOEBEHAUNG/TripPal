package com.codingdrama.trippal.repository

import com.codingdrama.trippal.model.network.KiaApiClient
import com.codingdrama.trippal.model.network.ErrorCode
import com.codingdrama.trippal.model.network.data.InstantSchedules

class FlightInfoRepository (private val apiClient: KiaApiClient) {
    suspend fun getFlightInfoArrive(): InstantSchedules {
        val domestic = apiClient.kiaApiService.getFlightInfoDomesticArrive()
        val international = apiClient.kiaApiService.getFlightInfoInternationalArrive()

        if (domestic.errorCode == ErrorCode.SUCCESS.code && international.errorCode == ErrorCode.SUCCESS.code) {
            // merge the two lists into an InstantSchedule list
            val domesticList = domestic.data?.instantSchedules!!
            domesticList.forEach { it.airLineType = 0 }
            val internationalList = international.data?.instantSchedules!!
            internationalList.forEach { it.airLineType = 1 }
            val result = domesticList + internationalList
            result.sortedBy { it.expectTime }
            return InstantSchedules(result)
        } else if (domestic.errorCode == ErrorCode.SUCCESS.code) {
            val domesticList = domestic.data?.instantSchedules!!
            domesticList.forEach { it.airLineType = 0 }
            return InstantSchedules(domesticList.sortedBy { it.expectTime })
        } else if (international.errorCode == ErrorCode.SUCCESS.code) {
            val internationalList = international.data?.instantSchedules!!
            internationalList.forEach { it.airLineType = 1 }
            return InstantSchedules(internationalList.sortedBy { it.expectTime })
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
            domesticList.forEach { it.airLineType = 0 }
            val internationalList = international.data?.instantSchedules!!
            internationalList.forEach { it.airLineType = 1 }
            val result = domesticList + internationalList
            result.sortedBy { it.expectTime }
            return InstantSchedules(result)
        } else if (domestic.errorCode == ErrorCode.SUCCESS.code) {
            val domesticList = domestic.data?.instantSchedules!!
            domesticList.forEach { it.airLineType = 0 }
            return InstantSchedules(domesticList.sortedBy { it.expectTime })
        } else if (international.errorCode == ErrorCode.SUCCESS.code) {
            val internationalList = international.data?.instantSchedules!!
            internationalList.forEach { it.airLineType = 1 }
            return InstantSchedules(internationalList.sortedBy { it.expectTime } ?: emptyList())
        } else {
            return InstantSchedules(emptyList())
        }
    }
}