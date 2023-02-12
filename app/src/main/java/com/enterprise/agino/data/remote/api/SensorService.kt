package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.CreateUserRequest
import retrofit2.Response
import retrofit2.http.GET

interface SensorService {
    // TODO: change this to the correct model
    @GET("api/AddSensor")
    suspend fun CreateSensor(): Response<CreateUserRequest>

}