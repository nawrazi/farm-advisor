package com.enterprise.agino.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("Title")
    val title: String,
)
