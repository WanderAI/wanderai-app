package com.thariqzs.wanderai.data.repository

import android.net.Uri
import android.util.Log
import com.thariqzs.wanderai.data.api.ApiService
import com.thariqzs.wanderai.utils.apiRequestFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

import javax.inject.Inject;

class HomeRepository @Inject constructor(
        private val apiService: ApiService,
) {
    val TAG = "hrthoriq"
    fun sendImage(uri: Uri) = apiRequestFlow {
        val file = File("/storage/emulated/0/Android/data/com.thariqzs.wanderai" + uri.path)
//        Log.d(TAG, "file: $file")
//        Log.d(TAG, "uri: ${uri}")
//        Log.d(TAG, "uri.path: ${uri.path}")
        file.let {
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

            // Set other request parameters
            val firstName = "THORIQ".toRequestBody(MultipartBody.FORM)
            val id = "foto_thoriq".toRequestBody(MultipartBody.FORM)

            apiService.sendImage(imagePart, firstName, id)
        }
    }
}
