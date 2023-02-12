package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.CreateFarmRequest
import retrofit2.Response
import retrofit2.http.GET

interface FieldService {
    // TODO: change this to the correct model
    @GET("api/AddFieldEndpoint")
    suspend fun CreateField(): Response<CreateFarmRequest>
}