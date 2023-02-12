package com.enterprise.agino.data.remote.dto

data class AddSensorResponse(
    val sensorId: String?,
    val serialNumber: String?,
    val lastCommunication: String?,
    val batteryStatus: Int?,
    val optimalGDD: Int?,
    val cuttingDateTimeCalculated: String?,
    val lastForecastDate: String?,
    val lat: Double?,
    val long: Double?,
    val state: Int?,
    val fieldId: String?
)