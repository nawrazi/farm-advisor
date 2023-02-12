package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.AddSensorRequest
import retrofit2.Response
import retrofit2.http.GET

interface SensorService {
    @GET("api/AddSensor")
    suspend fun AddSensor(): Response<AddSensorRequest>

}