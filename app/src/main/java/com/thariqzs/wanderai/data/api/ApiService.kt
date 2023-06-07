package com.thariqzs.wanderai.data.api

import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.data.api.model.DefaultResponse
import com.thariqzs.wanderai.data.api.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)

interface ApiService {
    @POST("user/login")
    suspend fun loginUser(
        @Body request: LoginRequest,
    ): Response<DefaultResponse<User>>

    @POST("user/register")
    suspend fun registerUser(
        @Body request: RegisterRequest,
    ): Response<DefaultResponse<User>>
}