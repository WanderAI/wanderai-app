package com.thariqzs.wanderai.data.api.model

import com.google.gson.annotations.SerializedName

sealed class ApiResponse<out T> {
    object Loading: ApiResponse<Nothing>()

    data class Success<out T>(
        val data: T,
    ): ApiResponse<T>()

    data class Failure(
        val errorMessage: String,
        val code: Int,
    ): ApiResponse<Nothing>()
}

data class ErrorResponse(
    val code: Int,
    val message: String
)