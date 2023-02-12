package com.enterprise.agino.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FieldResponse(
    @SerializedName("fieldId")
    val fieldID: String,

    val name: String,
    val alt: Long,
    val polygon: String,

    @SerializedName("farmId")
    val farmID: String,

    val sensors: List<SensorResponse>? = null
)
