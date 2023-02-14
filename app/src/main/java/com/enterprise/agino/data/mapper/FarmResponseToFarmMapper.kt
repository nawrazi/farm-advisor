package com.enterprise.agino.data.mapper

import com.enterprise.agino.data.remote.dto.FarmResponse
import com.enterprise.agino.domain.model.Farm

fun FarmResponse.toFarm() =
    Farm(
        farmID = farmID,
        name = name,
        postcode = postcode,
        city = city,
        country = country,
        notifications = notifications?.map { it.toNotification() }?.toList() ?: listOf(),
        fields = fields?.map { it.toField() } ?: emptyList(),
        userID = userID
    )