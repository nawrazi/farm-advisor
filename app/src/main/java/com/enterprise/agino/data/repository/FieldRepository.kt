package com.enterprise.agino.data.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.common.buildResource
import com.enterprise.agino.common.networkBoundResource
import com.enterprise.agino.data.mapper.toField
import com.enterprise.agino.data.remote.api.FieldService
import com.enterprise.agino.data.remote.dto.AddFieldRequest
import com.enterprise.agino.domain.model.Field
import com.enterprise.agino.domain.model.form.AddFieldForm
import com.enterprise.agino.domain.repository.IFarmRepository
import com.enterprise.agino.domain.repository.IFieldRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FieldRepository @Inject constructor(
    private val fieldService: FieldService,
    private val farmRepository: IFarmRepository
) : IFieldRepository {
    override fun getField(id: String): Flow<Resource<Field>> {
        return networkBoundResource(
            fetch = {
                fieldService.getField(id).body()!!
            },
            mapFetchedValue = {
                it.toField()
            }
        )
    }

    override fun addField(addFieldForm: AddFieldForm): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())

            val result = buildResource {
                fieldService.addField(
                    AddFieldRequest(
                        farmId = addFieldForm.farmId,
                        name = addFieldForm.name,
                        altitude = addFieldForm.altitude,
                        polygon = "rectangle"
                    )
                )
                return@buildResource
            }

            if (result is Resource.Success) {
                farmRepository.fetchFarm().collect()
            }
            emit(result)
        }.flowOn(Dispatchers.IO)

}