package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.AddFarmRequestDto
import com.enterprise.agino.data.remote.dto.FarmResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FarmService {

    @POST("api/AddFarmEndpoint")
    suspend fun addFarm(@Body addFarmRequestDto: AddFarmRequestDto): Response<FarmResponse>

    @GET("api/FarmApi/{id}")
    suspend fun getFarm(
        @Path("id") id: String,
        // TODO: add body parameters
    ): Response<FarmResponse>
}