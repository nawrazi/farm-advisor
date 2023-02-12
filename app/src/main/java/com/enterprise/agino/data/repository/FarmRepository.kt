package com.enterprise.agino.data.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.common.buildResource
import com.enterprise.agino.data.remote.api.FarmService
import com.enterprise.agino.data.remote.api.GeocodeService
import com.enterprise.agino.data.remote.dto.AddFarmRequest
import com.enterprise.agino.utils.toGeocodeUrl
import com.tomtom.sdk.location.GeoPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FarmRepository @Inject constructor(
    private val farmService: FarmService,
    private val geocodeService: GeocodeService,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun createBooking(addFarmRequest: AddFarmRequest): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())

            val result = buildResource {
                farmService.addFarm(addFarmRequest)
                return@buildResource
            }

            emit(result)
        }.flowOn(ioDispatcher)

    suspend fun latLngToPlace(geoPoint: GeoPoint): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())

            val result = buildResource {
                geocodeService.latLngToPlace(toGeocodeUrl(geoPoint))
                return@buildResource
            }

            emit(result)
        }.flowOn(ioDispatcher)

}