package com.thariqzs.wanderai.data.api.model

import com.google.gson.annotations.SerializedName

data class DefaultResponse<T> (
    @field:SerializedName("data")
    val data: T & Any,

    @field:SerializedName("message")
    val message: String,
)