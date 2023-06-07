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

interface ApiService {
    @POST("user/login")
    suspend fun loginUser(
        @Body request: LoginRequest,
    ): Response<DefaultResponse<User>>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
    ): Response<DefaultResponse<User>>
}