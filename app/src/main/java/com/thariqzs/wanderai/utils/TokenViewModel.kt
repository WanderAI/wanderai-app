package com.thariqzs.wanderai.utils

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thariqzs.wanderai.data.api.RegisterRequest
import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.data.api.model.DefaultResponse
import com.thariqzs.wanderai.data.api.model.User
import com.thariqzs.wanderai.data.api.model.User_ID
import com.thariqzs.wanderai.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val authRepository: AuthRepository
): BaseViewModel() {
val TAG = "tvmthoriq"
    val token = MutableLiveData<String?>()
    var email by mutableStateOf("")
    var name by mutableStateOf("")

    var _tokenResponse = MutableLiveData<ApiResponse<DefaultResponse<User_ID>>>()

    init {
        validateToken(object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    Log.d("tvm error thoriq", "onError: $message")
                }
            })
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getToken().collect {
                withContext(Dispatchers.Main) {
                    token.value = it
                }
            }
        }
    }

    fun saveToken(data: User) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "data.name: ${data.name}")
            Log.d(TAG, "data.email: ${data.email}")
            name = data.name.toString()
            email = data.email.toString()
            data.token?.let { tokenManager.saveToken(it) }
        }
    }

    fun deleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            name = ""
            email = ""
            tokenManager.deleteToken()
        }
    }

    fun validateToken(coroutinesErrorHandler: CoroutinesErrorHandler) {
        baseRequest(_tokenResponse, coroutinesErrorHandler) {
            authRepository.validateToken()
        }
    }
}