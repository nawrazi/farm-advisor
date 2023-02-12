package com.enterprise.agino.data.remote.dto

data class AddFarmRequest(
    val name: String,
    val postcode: String,
    val city: String,
    val country: String
)