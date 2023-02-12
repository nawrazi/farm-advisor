package com.enterprise.agino.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SensorResponse(
    @SerializedName("sensorId")
    val sensorID: String,

    val serialNumber: String? = null,
    val lastCommunication: String,
    val batteryStatus: Long,
    val optimalGDD: Long,
    val cuttingDateTimeCalculated: String,
    val lastForecastDate: String,
    val lat: Double,
    val long: Double,
    val state: Long,

    @SerializedName("fieldId")
    val fieldID: String
)
