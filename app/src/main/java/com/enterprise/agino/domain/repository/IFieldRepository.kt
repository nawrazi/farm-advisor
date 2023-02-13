package com.enterprise.agino.domain.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.data.remote.dto.AddFieldRequest
import com.enterprise.agino.domain.model.Field
import com.enterprise.agino.domain.model.form.AddFieldForm
import kotlinx.coroutines.flow.Flow

interface IFieldRepository {
    fun getField(id: String): Flow<Resource<Field>>
    fun addField(addFieldForm: AddFieldForm): Flow<Resource<Unit>>
}