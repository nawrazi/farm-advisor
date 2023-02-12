package com.enterprise.agino.data.remote.dto

data class AddFieldResponse(
    val fieldId: String,
    val name: String,
    val alt: Int,
    val polygon: String,
    val farmId: String,
    val sensors: Any?
)
