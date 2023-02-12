package com.enterprise.agino.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AddSensorResponse(
    val serialNumber: String,

    @SerializedName("lat")
    val latitude: Number,
    @SerializedName("longt")
    val longitude: Number,
    @SerializedName("fieldId")
    val fieldID: String
)