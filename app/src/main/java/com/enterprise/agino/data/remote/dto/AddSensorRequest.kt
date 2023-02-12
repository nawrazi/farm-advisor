package com.enterprise.agino.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AddSensorRequest(
    val serialNumber: String,

    @SerializedName("lat")
    val latitude: Number,
    @SerializedName("longt")
    val longitude: Number,
    @SerializedName("fieldId")
    val fieldID: String
)