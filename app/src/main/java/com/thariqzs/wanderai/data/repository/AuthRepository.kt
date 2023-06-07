package com.thariqzs.wanderai.data.repository

import com.thariqzs.wanderai.data.api.ApiService
import com.thariqzs.wanderai.data.api.LoginRequest
import com.thariqzs.wanderai.data.api.model.User
import com.thariqzs.wanderai.utils.apiRequestFlow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
) {
    fun login(payload: LoginRequest) = apiRequestFlow {
        apiService.loginUser(payload)
    }

    fun register(email: String, password: String, name: String) = apiRequestFlow {
        apiService.registerUser(email, password, name)
    }
}