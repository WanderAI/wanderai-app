package com.thariqzs.wanderai.data.api.model

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("uid")
    val uid: String?,

    @field:SerializedName("email")
    val email: String?,

    @field:SerializedName("token")
    val token: String?,

    @field:SerializedName("name")
    val name: String?
)