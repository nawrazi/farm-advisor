package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.CreateFarmRequest
import retrofit2.Response
import retrofit2.http.GET

interface FarmService {
    @GET("api/AddFieldEndpoint")
    suspend fun CreateFarm(): Response<CreateFarmRequest>
}