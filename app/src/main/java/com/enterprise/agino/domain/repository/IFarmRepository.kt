package com.enterprise.agino.domain.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.domain.AddFarmForm
import com.enterprise.agino.domain.model.Farm
import kotlinx.coroutines.flow.Flow

interface IFarmRepository {
    fun getFarm(id: String): Flow<Resource<Farm>>
    fun getFarm(): Flow<Farm?>
    suspend fun createFarm(addFarmForm: AddFarmForm): Flow<Resource<Unit>>
}