package com.enterprise.agino.data.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.common.buildResource
import com.enterprise.agino.common.networkBoundResource
import com.enterprise.agino.data.mapper.toField
import com.enterprise.agino.data.remote.api.FieldService
import com.enterprise.agino.data.remote.dto.AddFieldRequest
import com.enterprise.agino.domain.model.Field
import com.enterprise.agino.domain.repository.IFieldRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FieldRepository @Inject constructor(
    private val fieldService: FieldService,
    private val ioDispatcher: CoroutineDispatcher
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

    override fun addField(request: AddFieldRequest): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())

            val result = buildResource {
                fieldService.addField(request)
                return@buildResource
            }

            emit(result)
        }.flowOn(ioDispatcher)

}