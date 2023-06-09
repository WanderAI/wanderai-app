package com.thariqzs.wanderai.data.repository

import android.net.Uri
import android.util.Log
import com.thariqzs.wanderai.data.api.ApiService
import com.thariqzs.wanderai.utils.apiRequestFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

import javax.inject.Inject;

class HomeRepository @Inject constructor(
        private val apiService: ApiService,
) {
    fun sendImage(uri: Uri) = apiRequestFlow {
        val file = File(uri.path)
        file.let {
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val imagePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

            // Set other request parameters
            val firstName = RequestBody.create(MultipartBody.FORM, "THORIQ")
            val id = RequestBody.create(MultipartBody.FORM, "foto_thoriq")

            apiService.sendImage(imagePart, firstName, id)
        }
    }
}
