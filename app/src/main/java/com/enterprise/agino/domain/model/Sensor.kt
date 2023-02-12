package com.enterprise.agino.domain.model

data class Sensor(
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
    val fieldID: String
)
