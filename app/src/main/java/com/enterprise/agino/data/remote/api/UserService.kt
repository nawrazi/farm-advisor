package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("api/UserApi/{id}")
    suspend fun getUser(
        @Path("id") id: String,
    ): Response<UserResponse>
}