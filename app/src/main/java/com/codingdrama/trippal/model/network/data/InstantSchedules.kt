package com.codingdrama.trippal.model.network.data

import com.google.gson.annotations.SerializedName


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

// a data class for InstantSchedule
data class InstantSchedule(
    val expectTime: String,
    val realTime: String,
    val airLineName: String,
    val airLineCode: String,
    val airLineLogo: String,
    val airLineUrl: String,
    val airLineNum: String,
    var upAirportCode: String?,
    val upAirportName: String?,
    var goalAirportCode: String?,
    val goalAirportName: String?,
    val airPlaneType: String,
    val airBoardingGate: String,
    val airFlyStatus: String,
    val airFlyDelayCause: String
)

data class InstantSchedules (
    @SerializedName("InstantSchedule") val instantSchedules: List<InstantSchedule>
)

data class InstantSchedulesResponse (
    val data: InstantSchedules?,
    val message: String,
    val errorCode: Int
)