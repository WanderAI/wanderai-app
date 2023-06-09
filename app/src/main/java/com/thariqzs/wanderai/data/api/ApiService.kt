package com.thariqzs.wanderai.data.api

import com.thariqzs.wanderai.data.api.model.DefaultResponse
import com.thariqzs.wanderai.data.api.model.ImageData
import com.thariqzs.wanderai.data.api.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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

    @Multipart
    @POST("event/get-format")
    suspend fun sendImage(
        @Part image: MultipartBody.Part,
        @Part("FirstName") fname: RequestBody,
        @Part("Id") id: RequestBody
    ): Response<DefaultResponse<ImageData>>
}