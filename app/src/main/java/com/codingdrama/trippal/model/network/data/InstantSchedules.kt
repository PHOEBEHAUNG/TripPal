package com.codingdrama.trippal.model.network.data

import com.google.gson.annotations.SerializedName

// a data class for InstantSchedule
data class InstantSchedule(
    val expectTime: String,
    val realTime: String,
    val airLineName: String,
    val airLineCode: String,
    val airLineLogo: String,
    val airLineUrl: String,
    val airLineNum: String,
    var upAirportCode: String,
    val upAirportName: String,
    val airPlaneType: String,
    val airBoardingGate: String,
    val airFlyStatus: String,
    val airFlyDelayCause: String
)

data class InstantSchedules (
    @SerializedName("InstantSchedule") val instantSchedules: List<InstantSchedule>
)