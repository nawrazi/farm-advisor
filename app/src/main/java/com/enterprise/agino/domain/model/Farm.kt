package com.enterprise.agino.domain.model

data class Farm(
    val farmID: String,
    val name: String,
    val postcode: String,
    val city: String,
    val country: String,
    val notifications: String? = null,  // TODO: Change to List<Notification> as an object
    val fields: List<Field>,
    val userID: String
)
