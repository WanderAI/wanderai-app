package com.thariqzs.wanderai.data.api.model

data class Chat(
    val isUser: Boolean,
    val text: String,
    val actionType: Int? = 0,
    val result: Recommendation? = null
)

data class RequestUserAction(
    val label: String,
    val id: Int,
)

data class CityDetail(
    val cityName: String,
    val id: Int,
)

data class BudgetDetail(
    val amount: String,
    val id: Int,
)