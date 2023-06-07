package com.thariqzs.wanderai.data.repository

import com.thariqzs.wanderai.data.api.ApiService
import com.thariqzs.wanderai.utils.apiRequestFlow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
) {
    fun login(email: String, password: String) = apiRequestFlow {
        apiService.loginUser(email, password)
    }
}