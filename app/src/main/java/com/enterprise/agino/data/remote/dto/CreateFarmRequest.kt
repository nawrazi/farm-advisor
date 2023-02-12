package com.enterprise.agino.data.remote.dto

data class CreateFarmRequest(
    val name: String,
    val postcode: String,
    val city: String,
    val country: String
)