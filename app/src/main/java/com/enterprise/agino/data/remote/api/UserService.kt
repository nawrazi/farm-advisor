package com.enterprise.agino.data.remote.api

import com.enterprise.agino.data.remote.dto.CreateUserRequest
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("api/AddUserApi")
    suspend fun CreateUser(): Response<CreateUserRequest>
}