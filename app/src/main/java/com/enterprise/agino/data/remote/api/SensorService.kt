package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.AddSensorRequest
import com.enterprise.agino.data.remote.dto.AddSensorResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface SensorService {
    @GET("api/AddSensor")
    suspend fun addSensor(@Body request: AddSensorRequest): Response<AddSensorResponse>

}