package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.GraphResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GraphService {

    @GET
    suspend fun fetchGraphData(@Url url: String): Response<GraphResponse>

}