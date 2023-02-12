package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.AddFieldRequest
import com.enterprise.agino.data.remote.dto.AddFieldResponse
import com.enterprise.agino.data.remote.dto.FieldResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface FieldService {
    @GET("api/AddFieldEndpoint")
    suspend fun addField(@Body request: AddFieldRequest): Response<AddFieldResponse>

    @GET("api/FieldApi")
    suspend fun getField(id: String): Response<FieldResponse>
}