package com.enterprise.agino.data.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.common.buildResource
import com.enterprise.agino.data.remote.api.SensorService
import com.enterprise.agino.data.remote.dto.AddSensorRequest
import com.enterprise.agino.domain.model.AddSensorForm
import com.enterprise.agino.domain.repository.ISensorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SensorRepository @Inject constructor(
    private val sensorService: SensorService,
    private val ioDispatcher: CoroutineDispatcher
) : ISensorRepository {
    override fun addSensor(
        addSensorForm
        : AddSensorForm
    ): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            
            val result = buildResource {
                sensorService.addSensor(
                    AddSensorRequest(
                        serialNumber = addSensorForm.serialNumber,
                        lat = addSensorForm.location.latitude,
                        long = addSensorForm.location.longitude,
                        fieldId = addSensorForm.fieldId,
                        defaultGDD = addSensorForm.defaultGDD,
                        sensorInstallationDate = addSensorForm.sensorInstallationDate,
                        lastFieldCuttingDate = addSensorForm.lastFieldCuttingDate
                    )
                )
                return@buildResource
            }

            emit(result)
        }.flowOn(ioDispatcher)
}