package com.enterprise.agino.data.remote.dto

data class AddFieldRequest(
    val farmId: String,
    val name: String,
    val altitude: Int,
    val polygon: String
)
