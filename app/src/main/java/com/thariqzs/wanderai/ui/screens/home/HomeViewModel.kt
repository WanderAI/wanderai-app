package com.thariqzs.wanderai.ui.screens.home

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.data.api.model.DefaultResponse
import com.thariqzs.wanderai.data.api.model.ImageData
import com.thariqzs.wanderai.data.repository.HomeRepository
import com.thariqzs.wanderai.utils.BaseViewModel
import com.thariqzs.wanderai.utils.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    BaseViewModel() {
val TAG = "hvmthoriq"
    var showDialog by mutableStateOf(false)
    var imageUri by mutableStateOf(Uri.EMPTY)

    var _imageResponse = MutableLiveData<ApiResponse<DefaultResponse<ImageData>>>()

    fun printUri(){
        Log.d(TAG, "printUri: ${imageUri.path}")
        Log.d(TAG, "imageUri.path?.isNotEmpty(): ${imageUri.path?.isNotEmpty()}")
    }

    fun sendImage(coroutinesErrorHandler: CoroutinesErrorHandler) =
        baseRequest(_imageResponse, coroutinesErrorHandler) {
            Log.d("hvmthoriq", "imageUri: $imageUri")
            homeRepository.sendImage(imageUri)
        }
}