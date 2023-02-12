package com.enterprise.agino.domain.model


data class Field(
    val fieldID: String,
    val name: String,
    val alt: Long,
    val polygon: String,
    val farmID: String,

    val sensors: List<Sensor>
)
