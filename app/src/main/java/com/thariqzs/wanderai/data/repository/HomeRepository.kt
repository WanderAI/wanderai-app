package com.thariqzs.wanderai.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.thariqzs.wanderai.data.api.ApiService
import com.thariqzs.wanderai.data.api.model.DefaultResponse
import com.thariqzs.wanderai.data.api.model.ImageData
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
//        val bos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
//        val file: ByteArray = bos.toByteArray()
//        val file = File("/storage/emulated/0/Android/data/com.thariqzs.wanderai" + uri.path)
//        Log.d(TAG, "file: $file")
//        Log.d(TAG, "uri: ${uri}")
//        Log.d(TAG, "uri.path: ${uri.path}")
//        file.let {
        val requestFile = filetest.asRequestBody("image/*".toMediaTypeOrNull())
//        val file = RequestBody.create("image/*".toMediaTypeOrNull(),    filetest)
        val imagePart = MultipartBody.Part.createFormData("file", "image.jpeg", requestFile)
//
//            // Set other request parameters
//            val firstName = "THORIQ".toRequestBody(MultipartBody.FORM)
//            val id = "foto_thoriq".toRequestBody(MultipartBody.FORM)
//
//            apiService.sendImage(imagePart, firstName, id)
//        }

        apiService.sendImage(imagePart)
    }
}
