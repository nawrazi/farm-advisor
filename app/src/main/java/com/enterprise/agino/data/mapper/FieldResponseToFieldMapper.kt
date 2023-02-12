package com.enterprise.agino.data.mapper

import com.enterprise.agino.data.remote.dto.FieldResponse
import com.enterprise.agino.domain.model.Field

fun FieldResponse.toField() =
    Field(
        fieldID = fieldID,
        name = name,
        alt = alt,
        polygon = polygon,
        farmID = farmID,
        sensors = sensors?.map { it.toSensor() } ?: emptyList(),
    )