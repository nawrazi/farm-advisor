package com.enterprise.agino.domain.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.data.remote.dto.AddSensorRequest
import kotlinx.coroutines.flow.Flow

interface ISensorRepository {
    fun addSensor(request: AddSensorRequest): Flow<Resource<Unit>>
}