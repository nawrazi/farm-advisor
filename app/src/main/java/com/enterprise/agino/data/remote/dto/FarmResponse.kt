package com.enterprise.agino.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FarmResponse(
    @SerializedName("farmId")
    val farmID: String,

    val name: String,
    val postcode: String,
    val city: String,
    val country: String,
    val notifications: List<NotificationDto>? = null,
    val fields: List<FieldResponse>? = null,

    @SerializedName("userId")
    val userID: String
)
