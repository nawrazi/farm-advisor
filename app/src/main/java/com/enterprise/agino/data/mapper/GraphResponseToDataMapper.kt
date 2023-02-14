package com.enterprise.agino.data.mapper

import com.enterprise.agino.data.remote.dto.GraphResponse
import com.enterprise.agino.domain.model.GraphData
import com.enterprise.agino.domain.model.GraphTimeStamp

fun GraphResponse.toGraphData(): GraphData {
    val timeStamps = mutableListOf<GraphTimeStamp>()

    this.properties.timeseries.forEach {
        timeStamps.add(
             GraphTimeStamp(
                 time = it.time,
                 pressure = it.data.instant.details["air_pressure_at_sea_level"] ?: 0.0,
                 temperature = it.data.instant.details["air_temperature"] ?: 0.0,
                 dewTemperature = it.data.instant.details["dew_point_temperature"] ?: 0.0,
                 humidity = it.data.instant.details["relative_humidity"] ?: 0.0,
                 wind = it.data.instant.details["wind_from_direction"] ?: 0.0,
             )
        )
    }

    return GraphData(timeStamps)
}