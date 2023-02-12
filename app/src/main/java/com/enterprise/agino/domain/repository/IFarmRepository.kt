package com.enterprise.agino.domain.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.domain.model.Farm
import kotlinx.coroutines.flow.Flow

interface IFarmRepository {
    fun GetFarm(id: String): Flow<Resource<Farm>>
}