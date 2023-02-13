package com.enterprise.agino.domain.model

import com.tomtom.sdk.location.GeoPoint
import java.time.Instant

data class AddSensorForm(
    val serialNumber: String,
    val location: GeoPoint,
    val fieldId: String,
    val defaultGDD: Int,
    val sensorInstallationDate: Instant,
    val lastFieldCuttingDate: Instant,
)
