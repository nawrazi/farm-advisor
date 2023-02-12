package com.enterprise.agino.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val userID: String,
    val name: String,
    val email: String,
    val phone: String,

    @SerializedName("authId")
    val authID: String,

    val farms: List<FarmResponse>? = null
)
