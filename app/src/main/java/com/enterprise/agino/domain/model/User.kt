package com.enterprise.agino.domain.model

data class User(
    val userID: String,
    val name: String,
    val email: String,
    val phone: String,
    val authID: String,
    val farms: List<Farm>
)
