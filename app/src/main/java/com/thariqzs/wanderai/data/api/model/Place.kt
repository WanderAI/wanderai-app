package com.thariqzs.wanderai.data.api.model

import com.google.gson.annotations.SerializedName

data class PlaceDetail(
    @field:SerializedName("nama")
    val nama: String?,

    @field:SerializedName("summary")
    val summary: String?,

    @field:SerializedName("rating_tourism")
    val rating_tourism: Float?,

    @field:SerializedName("important_facts")
    val important_facts: List<String>?,

    @field:SerializedName("sejarah")
    val sejarah: String?,
)

data class Place(
    @field:SerializedName("prediction")
    val nama: String? = null,

    @field:SerializedName("detail")
    val detail: PlaceDetail? = null,

    @field:SerializedName("probability")
    val probability: Float? = null,
)