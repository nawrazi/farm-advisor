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
    val sensorInstallationDate: Date,
    val lastFieldCuttingDate: Date,

    // NOTICE: These fields are required but not present in the form
    val lastCommunication: Date = Date(Instant.now().toEpochMilli()),
    val batteryStatus: Int = 100,
    val cuttingDateTimeCalculated: Date = Date(Instant.now().toEpochMilli()),
    val lastForecastData: Date = Date(Instant.now().toEpochMilli()),
    val state: String = "Working"
)
