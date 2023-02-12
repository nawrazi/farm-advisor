package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.FieldResponse
import retrofit2.Response
import retrofit2.http.GET

interface FieldService {
    // TODO: change this to the correct model
//    @GET("api/AddFieldEndpoint")
//    suspend fun AddField(): Response<CreateFarmRequest>

    @GET("api/FieldApi")
    suspend fun GetField(id: String): Response<FieldResponse>
}