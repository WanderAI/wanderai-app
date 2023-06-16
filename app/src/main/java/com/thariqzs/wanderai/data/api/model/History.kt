package com.thariqzs.wanderai.data.api.model

import com.google.gson.annotations.SerializedName

data class History(
    @field:SerializedName("doc_id")
    val doc_id: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("date_start")
    val date_start: String? = null,

    @field:SerializedName("date_end")
    val date_end: String? = null,
)

data class HistoryDetail(
    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("created_date")
    val created_date: String? = null,

    @field:SerializedName("user_id")
    val user_id: String? = null,

    @field:SerializedName("date_start")
    val date_start: String? = null,

    @field:SerializedName("date_end")
    val date_end: String? = null,

    @field:SerializedName("data")
    val data: HistoryData? = null,

    @field:SerializedName("description")
    val description: String? = null
)

data class HistoryData(
    @field:SerializedName("tourism_lists_each_day")
    val tourism_lists_each_day: List<List<TourismData>>?,

    @field:SerializedName("restaurants_recommendations_each_day")
    val restaurants_recommendations_each_day: List<List<RestaurantData>>?,

    @field:SerializedName("accommodations_recommendations")
    val accommodations_recommendations: List<AccomodationData>?,

    @field:SerializedName("cost_minimum_per_person")
    val cost_minimum_per_person: Int?,

    @field:SerializedName("cost_maximum_per_person")
    val cost_maximum_per_person: Int?,

    @field:SerializedName("total_cost_minimum")
    val total_cost_minimum: Int?,

    @field:SerializedName("total_cost_maximum")
    val total_cost_maximum: Int?,
)

data class TourismData(
    @field:SerializedName("place_id")
    val place_id: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("image_link")
    val image_link: String?,

    @field:SerializedName("description")
    val description: String?,

    @field:SerializedName("category")
    val category: String?,

    @field:SerializedName("city")
    val city: String?,

    @field:SerializedName("rating")
    val rating: Float?,

    @field:SerializedName("geometry_location_lat")
    val geometry_location_lat: Float?,

    @field:SerializedName("geometry_location_lng")
    val geometry_location_lng: Float?,

    @field:SerializedName("formatted_address")
    val formatted_address: String?,

    @field:SerializedName("cost_range_min")
    val cost_range_min: Int?,

    @field:SerializedName("cost_range_max")
    val cost_range_max: Int?,
    )

data class RestaurantData(
    @field:SerializedName("place_id")
    val place_id: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("link_restaurant")
    val link_restaurant: String?,

    @field:SerializedName("formatted_address")
    val formatted_address: String?,

    @field:SerializedName("geometry_location_lat")
    val geometry_location_lat: Float?,

    @field:SerializedName("geometry_location_lng")
    val geometry_location_lng: Float?,

    @field:SerializedName("tipe_makanan")
    val tipe_makanan: String?,

    @field:SerializedName("level_price")
    val level_price: Int?,

    @field:SerializedName("rating")
    val rating: Float?,

    @field:SerializedName("cost_range_min")
    val cost_range_min: Int?,

    @field:SerializedName("cost_range_max")
    val cost_range_max: Int?,

    @field:SerializedName("distance_part_of_cluster")
    val distance_part_of_cluster: Float?,
)

data class AccomodationData(
    @field:SerializedName("place_icon_image")
    val place_icon_image: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("formatted_address")
    val formatted_address: String?,

    @field:SerializedName("geometry_location_lat")
    val geometry_location_lat: Float?,

    @field:SerializedName("geometry_location_lng")
    val geometry_location_lng: Float?,

    @field:SerializedName("acommodation_type")
    val acommodation_type: String?,

    @field:SerializedName("lokasi")
    val lokasi: String?,

    @field:SerializedName("rate_level")
    val rate_level: String?,

    @field:SerializedName("rating")
    val rating: Float?,

    @field:SerializedName("num_of_reviews")
    val num_of_reviews: Int?,

    @field:SerializedName("price_per_night")
    val price_per_night: String?,

    @field:SerializedName("distance_avg")
    val distance_avg: Float?,
)