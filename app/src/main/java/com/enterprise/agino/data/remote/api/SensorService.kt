package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.AddSensorRequest
import com.enterprise.agino.data.remote.dto.AddSensorResponse
import com.enterprise.agino.data.remote.dto.SensorResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SensorService {

    @POST("api/AddSensor")
    suspend fun addSensor(@Body request: AddSensorRequest): Response<AddSensorResponse>

    @GET("api/allSensors/{id}")
    suspend fun getSensors(@Path("id") id: String): Response<List<SensorResponse>>

}