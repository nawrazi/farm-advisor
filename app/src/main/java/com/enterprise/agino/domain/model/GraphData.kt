package com.enterprise.agino.domain.model

data class GraphData(
    val timeStamps: List<GraphTimeStamp>
)

data class GraphTimeStamp(
    val time: String,
    val pressure: Double,
    val temperature: Double,
    val dewTemperature: Double,
    val humidity: Double,
    val wind: Double
)