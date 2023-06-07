package com.thariqzs.wanderai.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thariqzs.wanderai.data.api.ApiService
import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.data.api.model.User
import com.thariqzs.wanderai.data.repository.AuthRepository
import com.thariqzs.wanderai.utils.BaseViewModel
import com.thariqzs.wanderai.utils.CoroutinesErrorHandler
import com.thariqzs.wanderai.utils.apiRequestFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (private val authRepository: AuthRepository) : BaseViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordConf by mutableStateOf("")
    var name by mutableStateOf("")
    var currTab by mutableStateOf("Login")

    private val _loginResponse = MutableLiveData<ApiResponse<User>>()
    val loginResponse = _loginResponse

    fun login(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest( _loginResponse, coroutinesErrorHandler) {
        authRepository.login(email,password)
    }
}