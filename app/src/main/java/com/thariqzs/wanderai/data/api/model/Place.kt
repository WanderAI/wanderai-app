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

    @field:SerializedName("image_url")
    val image_url: String?,

    @field:SerializedName("restaurant")
    val restaurant: List<PlaceRestaurantData>?,
)

data class Place(
    @field:SerializedName("prediction")
    val nama: String? = null,

    @field:SerializedName("detail")
    val detail: PlaceDetail? = null,

    @field:SerializedName("probability")
    val probability: Float? = null,
)

data class PlaceRestaurantData(
    @SerializedName("par_id")
    val par_id: String? = null,

    @SerializedName("distance_part_of_cluster")
    val distance_part_of_cluster: Float? = null,

    @SerializedName("place_id")
    val place_id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("business_status")
    val business_status: String? = null,

    @SerializedName("rating")
    val rating: Float? = null,

    @SerializedName("user_ratings_total")
    val user_ratings_total: Int? = null,

    @SerializedName("vicinity")
    val vicinity: String? = null,

    @SerializedName("geometry_location_lat")
    val geometry_location_lat: Float? = null,

    @SerializedName("geometry_location_lng")
    val geometry_location_long: Float? = null,

    @SerializedName("popularity")
    val popularity: Float? = null
)