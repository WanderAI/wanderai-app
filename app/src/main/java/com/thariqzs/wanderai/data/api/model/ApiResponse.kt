package com.thariqzs.wanderai.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @field:SerializedName("data")
    val error: T? = null,

    @field:SerializedName("message")
    val message: String? = null,
)