package com.enterprise.agino.domain.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.domain.model.AddSensorForm
import kotlinx.coroutines.flow.Flow

interface ISensorRepository {
    fun addSensor(request: AddSensorForm): Flow<Resource<Unit>>
}