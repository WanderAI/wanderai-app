package com.thariqzs.wanderai.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import com.thariqzs.wanderai.data.api.LoginRequest
import com.thariqzs.wanderai.data.api.RegisterRequest
import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.data.api.model.DefaultResponse
import com.thariqzs.wanderai.data.api.model.User
import com.thariqzs.wanderai.data.repository.AuthRepository
import com.thariqzs.wanderai.utils.BaseViewModel
import com.thariqzs.wanderai.utils.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {
    var currTab by mutableStateOf("Login")

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordConf by mutableStateOf("")
    var name by mutableStateOf("")

    var nameErr by mutableStateOf("")
    var emailErr by mutableStateOf("")
    var passConfErr by mutableStateOf("")
    var passErr by mutableStateOf("")

    private var _loginResponse = MutableLiveData<ApiResponse<DefaultResponse<User>>>()
    var loginResponse = _loginResponse

    fun login(coroutinesErrorHandler: CoroutinesErrorHandler) =
        baseRequest(_loginResponse, coroutinesErrorHandler) {
            val payload = LoginRequest(email, password)
            authRepository.login(payload)
        }

    fun register(coroutinesErrorHandler: CoroutinesErrorHandler) =
        baseRequest(_loginResponse, coroutinesErrorHandler) {
            val payload = RegisterRequest(email, password, name)
            authRepository.register(payload)
        }

    fun validateInputLogin() {
        emailErr = if (email.isNotEmpty() && !validateEmail(email)) {
            "Email tidak valid"
        } else {
            ""
        }

        passErr = if (password.isNotEmpty() && password.length < 8) {
            "Password harus memiliki setidaknya 8 karakter"
        } else {
            ""
        }
    }

    fun validateInputRegister() {
        emailErr = if (email.isEmpty()) {
            "Harap lengkapi email"
        } else if (!validateEmail(email)) {
            "Email tidak valid"
        } else {
            ""
        }

        passErr = if (password.isEmpty()) {
            "Harap lengkapi password"
        } else if (!validatePassword(password)) {
            "Password harus mengandung angka dan karakter"
        } else if (password.length < 8) {
            "Password harus memiliki setidaknya 8 karakter"
        } else {
            ""
        }

        passConfErr = if (passwordConf.isEmpty()) {
            "Harap lengkapi konfirmasi password"
        } else if (password != passwordConf) {
            "Konfirmasi password tidak cocok"
        } else {
            ""
        }

        nameErr = if (name.isEmpty()) {
            "Harap lengkapi nama"
        } else if (name.contains(Regex("\\p{So}"))) {
            "Nama tidak boleh mengandung emoji"
        } else {
            ""
        }
    }


    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validatePassword(password: String): Boolean {
        val regex = Regex(".*\\d.*")
        return password.length >= 8 && regex.matches(password)
    }

    companion object {
        val TAG = "avmthoriq"
    }
}