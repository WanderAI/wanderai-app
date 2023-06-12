package com.thariqzs.wanderai.data.repository

import com.thariqzs.wanderai.data.api.ApiService
import javax.inject.Inject

class TravelPlanningRepository @Inject constructor(
    private val apiService: ApiService,
) {
    val TAG = "tprthoriq"

}