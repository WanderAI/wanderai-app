package com.thariqzs.wanderai.data.api

import com.thariqzs.wanderai.data.api.model.DefaultResponse
import com.thariqzs.wanderai.data.api.model.History
import com.thariqzs.wanderai.data.api.model.HistoryDetail
import com.thariqzs.wanderai.data.api.model.Place
import com.thariqzs.wanderai.data.api.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
    @POST("event/recommendation-by-image")
    suspend fun sendImage(
        @Part image: MultipartBody.Part,
    ): Response<DefaultResponse<Place>>

    @GET("event/list-recommendation-history")
    suspend fun getHistory(): Response<DefaultResponse<List<History>>>

    @GET("event/recommendation-detail/{id}")
    suspend fun getDetail(@Path("id") id: String): Response<DefaultResponse<HistoryDetail>>
}