package com.enterprise.agino.domain.model

data class Farm(
    val farmID: String,
    val name: String,
    val postcode: String,
    val city: String,
    val country: String,
    val notifications: String? = null,
    val fields: String? = null,
    val userID: String
)
