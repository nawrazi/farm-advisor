package com.enterprise.agino.domain.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.domain.model.Field
import kotlinx.coroutines.flow.Flow

interface IFieldRepository {
    fun getField(id: String): Flow<Resource<Field>>
}