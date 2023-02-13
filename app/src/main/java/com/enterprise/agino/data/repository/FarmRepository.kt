package com.enterprise.agino.data.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.common.buildResource
import com.enterprise.agino.common.networkBoundResource
import com.enterprise.agino.data.local.LocalPrefStore
import com.enterprise.agino.data.mapper.toFarm
import com.enterprise.agino.data.remote.api.FarmService
import com.enterprise.agino.data.remote.dto.AddFarmRequestDto
import com.enterprise.agino.domain.AddFarmForm
import com.enterprise.agino.domain.model.Farm
import com.enterprise.agino.domain.repository.IFarmRepository
import com.tomtom.sdk.common.ifFailure
import com.tomtom.sdk.common.ifSuccess
import com.tomtom.sdk.search.reversegeocoder.ReverseGeocoder
import com.tomtom.sdk.search.reversegeocoder.ReverseGeocoderOptions
import com.tomtom.sdk.search.reversegeocoder.ReverseGeocoderResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FarmRepository @Inject constructor(
    private val farmService: FarmService,
    private val reverseGeocoder: ReverseGeocoder,
    private val localPrefStore: LocalPrefStore
) : IFarmRepository {

    override fun getFarm(): Flow<Farm?> = localPrefStore.getFarm()

    override suspend fun createFarm(addFarmForm: AddFarmForm): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())

            var reverseGeocoderResponse: ReverseGeocoderResponse? = null

            reverseGeocoder.reverseGeocode(ReverseGeocoderOptions(position = addFarmForm.geoPosition))
                .ifFailure {
                    emit(Resource.Error(it.message))
                    return@flow
                }.ifSuccess {
                    reverseGeocoderResponse = it
                }

            val result = buildResource {
                val address = reverseGeocoderResponse?.places?.get(0)?.place?.address

                farmService.addFarm(
                    AddFarmRequestDto(
                        userId = "userId",
                        name = addFarmForm.name,
                        postcode = address?.postalCode ?: "",
                        city = address?.municipality ?: "",
                        country = address?.country ?: ""
                    )
                )
                return@buildResource
            }

            emit(result)
        }.flowOn(Dispatchers.IO)

    override fun getFarm(id: String): Flow<Resource<Farm>> {
        return networkBoundResource(
            fetch = {
                farmService.getFarm(id).body()!!
            },
            mapFetchedValue = {
                it.toFarm()
            }
        )
    }
}