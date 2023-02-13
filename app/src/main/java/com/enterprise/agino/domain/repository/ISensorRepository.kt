package com.enterprise.agino.domain.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.domain.model.AddSensorForm
import com.enterprise.agino.domain.model.Sensor
import kotlinx.coroutines.flow.Flow

interface ISensorRepository {
    fun addSensor(addSensorForm: AddSensorForm): Flow<Resource<Unit>>

    fun getSensors(id: String): Flow<Resource<List<Sensor>>>
}