package com.thariqzs.wanderai.data.repository

import com.thariqzs.wanderai.data.api.ApiService
import com.thariqzs.wanderai.data.api.PreferenceRequest
import com.thariqzs.wanderai.data.api.RandomRequest
import com.thariqzs.wanderai.utils.apiRequestFlow
import javax.inject.Inject

class TravelPlanningRepository @Inject constructor(
    private val apiService: ApiService,
) {
    val TAG = "tprthoriq"

    fun requestRandom(payload: RandomRequest) = apiRequestFlow {
        apiService.requestRandom(payload)
    }

    fun requestWithPreference(payload: PreferenceRequest) = apiRequestFlow {
        apiService.requestWithPreference(payload)
    }
}