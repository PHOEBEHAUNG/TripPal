package com.codingdrama.trippal.model.network.interfaces

import com.codingdrama.trippal.model.network.data.InstantSchedules
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * https://www.kia.gov.tw/API/InstantSchedule.ashx?AirFlyLine=2&AirFlyIO=2
 * Fetch flight information from the url.
 *
 *   {"InstantSchedule": [
 *     {
 *       "expectTime": "09:00",
 *       "realTime": "08:49",
 *       "airLineName": "立榮航空",
 *       "airLineCode": "UIA",
 *       "airLineLogo": "https://www.kia.gov.tw/images/airlines/B7.gif",
 *       "airLineUrl": "https://www.kia.gov.tw/AirFlyCompany.aspx?Code=UIA",
 *       "airLineNum": "B78690",
 *       "upAirportCode": "MZG",
 *       "upAirportName": "澎湖",
 *       "airPlaneType": "AT76",
 *       "airBoardingGate": "17",
 *       "airFlyStatus": "抵達Arrived",
 *       "airFlyDelayCause": ""
 *     },
 *     {
 *       "expectTime": "09:25",
 *       "realTime": "09:12",
 *       "airLineName": "立榮航空",
 *       "airLineCode": "UIA",
 *       "airLineLogo": "https://www.kia.gov.tw/images/airlines/B7.gif",
 *       "airLineUrl": "https://www.kia.gov.tw/AirFlyCompany.aspx?Code=UIA",
 *       "airLineNum": "B79162",
 *       "upAirportCode": "MZG",
 *       "upAirportName": "澎湖",
 *       "airPlaneType": "AT76",
 *       "airBoardingGate": "15",
 *       "airFlyStatus": "抵達Arrived",
 *       "airFlyDelayCause": ""
 *     }]
 *   }
 */

interface KIAApiService {
    // AirFlyLine : 2: Domestic, 1: International
    // AirFlyIO : 1: Departure from KHH, 2: Arrive at KHH
    @GET("API/InstantSchedule.ashx")
    suspend fun getFlightInfoDomesticDeparture(@Query("AirFlyLine") airFlyLine: Int = 1, @Query("AirFlyIO") airFlyIO: Int = 1): InstantSchedules

    @GET("API/InstantSchedule.ashx")
    suspend fun getFlightInfoDomesticArrive(@Query("AirFlyLine") airFlyLine: Int = 1, @Query("AirFlyIO") airFlyIO: Int = 2): InstantSchedules

    @GET("API/InstantSchedule.ashx")
    suspend fun getFlightInfoInternationalDeparture(@Query("AirFlyLine") airFlyLine: Int = 2, @Query("AirFlyIO") airFlyIO: Int = 1): InstantSchedules

    @GET("API/InstantSchedule.ashx")
    suspend fun getFlightInfoInternationalArrive(@Query("AirFlyLine") airFlyLine: Int = 2, @Query("AirFlyIO") airFlyIO: Int = 2): InstantSchedules
}