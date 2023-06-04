package com.thariqzs.wanderai.data.api

import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.data.api.model.User
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<ApiResponse<User>>

    @POST("register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<ApiResponse<User>>
}