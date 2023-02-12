package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.AddFarmRequest
import com.enterprise.agino.data.remote.dto.AddFarmResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FarmService {

    @POST("api/AddFarmEndpoint")
    suspend fun addFarm(@Body addFarmRequest: AddFarmRequest): Response<AddFarmResponse>

}