package com.thariqzs.wanderai.data.api.model

import com.google.gson.annotations.SerializedName

data class ImageData(
    @field:SerializedName("confidence_percent")
    val confidence_percent: Float?,

    @field:SerializedName("place")
    val place: ImageMetaData?,

    @field:SerializedName("name")
    val name: String?
)

data class ImageMetaData(
    @field:SerializedName("nama")
    val nama: String?,

    @field:SerializedName("summary")
    val summary: String?,

    @field:SerializedName("rating_tourism")
    val rating_tourism: Float?,

    @field:SerializedName("nearest_restaurants")
    val nearest_restaurants: List<Restaurant>?,

    @field:SerializedName("important_unique_facts")
    val important_unique_facts: List<String>?,

    @field:SerializedName("sejarah")
    val sejarah: String?
)

data class Restaurant(
    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("jarak_dari_tempat_meter")
    val jarak_dari_tempat_meter: Int?,

    @field:SerializedName("kategori_harga")
    val kategori_harga: Int?,

    @field:SerializedName("rating_restaurant")
    val rating_restaurant: Float?,
)
