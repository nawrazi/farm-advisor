package com.enterprise.agino.data.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.common.networkBoundResource
import com.enterprise.agino.data.mapper.toFarm
import com.enterprise.agino.data.remote.api.FarmService
import com.enterprise.agino.domain.model.Farm
import com.enterprise.agino.domain.repository.IFarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FarmRepository @Inject constructor(
    private val farmService: FarmService
) : IFarmRepository {
    override fun GetFarm(id: String): Flow<Resource<Farm>> {
        return networkBoundResource(
            fetch = {
                farmService.GetFarm(id).body()!!
            },
            mapFetchedValue = {
                it.toFarm()
            }
        )
    }

}