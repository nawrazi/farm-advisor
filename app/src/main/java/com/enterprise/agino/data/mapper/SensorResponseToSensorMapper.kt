package com.enterprise.agino.data.mapper

import com.enterprise.agino.data.remote.dto.SensorResponse
import com.enterprise.agino.domain.model.Sensor

fun SensorResponse.toSensor() =
    Sensor(
        sensorID = sensorID,
        serialNumber = serialNumber,
        lastCommunication = lastCommunication,
        batteryStatus = batteryStatus,
        optimalGDD = optimalGDD,
        cuttingDateTimeCalculated = cuttingDateTimeCalculated,
        lastForecastDate = lastForecastDate,
        lat = lat,
        long = long,
        state = state,
        fieldID = fieldID
    )