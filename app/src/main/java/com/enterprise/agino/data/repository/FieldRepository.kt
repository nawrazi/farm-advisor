package com.enterprise.agino.data.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.common.networkBoundResource
import com.enterprise.agino.data.mapper.toField
import com.enterprise.agino.data.remote.api.FieldService
import com.enterprise.agino.domain.model.Field
import com.enterprise.agino.domain.repository.IFieldRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FieldRepository @Inject constructor(
    private val fieldService: FieldService
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
}