package com.enterprise.agino.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AddUserRequest(
    val name: String,
    val email: String,
    val phone: String,

    @SerializedName("authId")
    val authID: String
)
