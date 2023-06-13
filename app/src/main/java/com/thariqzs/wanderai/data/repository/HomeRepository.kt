package com.thariqzs.wanderai.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.thariqzs.wanderai.data.api.ApiService
import com.thariqzs.wanderai.data.api.model.DefaultResponse
import com.thariqzs.wanderai.utils.apiRequestFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
) {
    val TAG = "hrthoriq"
    fun sendImage(filetest: File) = apiRequestFlow {
        val requestFile = filetest.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("file", "image.jpeg", requestFile)

        apiService.sendImage(imagePart)
    }

    fun getHistoryList() = apiRequestFlow {
        apiService.getHistory()
    }

    fun getHistoryDetail(id: String) = apiRequestFlow {
        apiService.getDetail(id)
    }
}
