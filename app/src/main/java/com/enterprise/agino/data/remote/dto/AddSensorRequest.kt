package com.enterprise.agino.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.util.*

data class AddSensorRequest(
    val serialNumber: String,
    val lat: Double,
    @SerializedName("longt")
    val long: Double,
    val fieldId: String,
    @SerializedName("optimalGDD")
    val defaultGDD: Int,
    val sensorInstallationDate: Instant,
    val lastFieldCuttingDate: Instant,

    // NOTICE: These fields are required but not present in the form
    val lastCommunication: String = "",
    val batteryStatus: Int = 100,
    val cuttingDateTimeCalculated: String = "",
    val lastForecastData: String = "",
    val state: String = "working"
)
