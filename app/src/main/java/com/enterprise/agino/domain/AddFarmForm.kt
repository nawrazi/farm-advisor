package com.enterprise.agino.domain

import com.tomtom.sdk.location.GeoPoint

data class AddFarmForm(
    val name: String,
    val geoPosition: GeoPoint,
)
