package com.thariqzs.wanderai.utils

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            tokenManager.getToken().first()
        }

        return runBlocking {
            token?.let { tokenManager.saveToken(it) }
            response.request.newBuilder()
                .header("Authorization", "Bearer ${token}")
                .build()
        }
    }
}