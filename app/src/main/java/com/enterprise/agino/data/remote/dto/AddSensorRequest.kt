package com.enterprise.agino.data.remote.dto

data class AddSensorRequest(
    val lastCommunication: String,
    val batteryStatus: Int,
    val optimalGDD: Int,
    val cuttingDateTimeCalculated: String,
    val lastForecastData: String,
    val lat: Double,
    val longt: Double,
    val state: String
)
