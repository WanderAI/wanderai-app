package com.thariqzs.wanderai.data.api.model

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("uid")
    val userId: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("token")
    val token: String?
)