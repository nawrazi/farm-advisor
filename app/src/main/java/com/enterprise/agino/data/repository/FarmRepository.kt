package com.enterprise.agino.data.repository

import com.enterprise.agino.common.Resource
import com.enterprise.agino.common.buildResource
import com.enterprise.agino.data.local.LocalPrefStore
import com.enterprise.agino.data.mapper.toFarm
import com.enterprise.agino.data.remote.api.FarmService
import com.enterprise.agino.data.remote.api.UserService
import com.enterprise.agino.data.remote.dto.AddFarmRequestDto
import com.enterprise.agino.domain.model.Farm
import com.enterprise.agino.domain.model.form.AddFarmForm
import com.enterprise.agino.domain.repository.IFarmRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
    private val userService: UserService,
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

            val userResult = buildResource {
                return@buildResource userService.getUser(Firebase.auth.currentUser!!.uid).body()!!
            }

            if (userResult is Resource.Error) {
                emit(Resource.Error(userResult.message))
                return@flow
            }

            val userId = userResult.value!!.userID
            val result = buildResource {
                val address = reverseGeocoderResponse?.places?.get(0)?.place?.address

                val response = farmService.addFarm(
                    AddFarmRequestDto(
                        userId = userId,
                        name = addFarmForm.name,
                        postcode = address?.postalCode ?: "",
                        city = address?.municipality ?: "",
                        country = address?.country ?: ""
                    )
                )

                if (response.isSuccessful) {
                    localPrefStore.setFarm(response.body()!!.toFarm())
                }
                return@buildResource
            }

            emit(result)
        }.flowOn(Dispatchers.IO)

    override suspend fun fetchFarm(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        val userResult = buildResource {
            return@buildResource userService.getUser(Firebase.auth.currentUser!!.uid).body()!!
        }

        if (userResult is Resource.Error) {
            emit(Resource.Error(userResult.message))
            return@flow
        }

        if (userResult is Resource.Success && userResult.value!!.farms == null) {
            localPrefStore.setFarm(null)
            emit(Resource.Success(Unit))
            return@flow
        }

        val farmResult = buildResource {
            if (userResult.value!!.farms == null || userResult.value.farms!!.isEmpty()) {
                return@buildResource null
            }
            return@buildResource farmService.getFarm(userResult.value.farms[0].farmID).body()!!
        }

        if (farmResult is Resource.Error) {
            emit(Resource.Error(farmResult.message))
            return@flow
        }

        localPrefStore.setFarm(farmResult.value?.toFarm())
        emit(Resource.Success(Unit))
    }.flowOn(Dispatchers.IO)
}
