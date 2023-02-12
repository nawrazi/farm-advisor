package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.ReverseGeocodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GeocodeService {

    @GET
    suspend fun latLngToPlace(@Url url: String): Response<ReverseGeocodeResponse>

}