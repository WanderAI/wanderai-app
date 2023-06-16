package com.thariqzs.wanderai.ui.screens.home

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.data.api.model.DefaultResponse
import com.thariqzs.wanderai.data.api.model.History
import com.thariqzs.wanderai.data.api.model.HistoryDetail
import com.thariqzs.wanderai.data.api.model.Place
import com.thariqzs.wanderai.data.repository.HomeRepository
import com.thariqzs.wanderai.utils.BaseViewModel
import com.thariqzs.wanderai.utils.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    BaseViewModel() {
    val TAG = "hvmthoriq"
    var showDialog by mutableStateOf(false)
    var imageUri by mutableStateOf(Uri.EMPTY)
    var bitmap by mutableStateOf(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
    var testfile by mutableStateOf(File(""))

    var _placeResponse = MutableLiveData<ApiResponse<DefaultResponse<Place>>>()
    var _planHistoryResponse = MutableLiveData<ApiResponse<DefaultResponse<List<History>>>>()
    var _historyDetailResponse = MutableLiveData<ApiResponse<DefaultResponse<HistoryDetail>>>()

    var place by mutableStateOf(Place())
    var history by mutableStateOf(emptyList<History>())
    var historyDetail by mutableStateOf(HistoryDetail())

    var navigationCompleted by mutableStateOf(false)

//    init {
//        Log.d(TAG, "init: ")
//        getPlanHistory(
//            object : CoroutinesErrorHandler {
//                override fun onError(message: String) {
//                    Log.d("hsthoriq senimage", "onError: $message")
//                }
//            })
//    }

    fun printUri() {
        Log.d(TAG, "printUri: ${imageUri.path}")
        Log.d(TAG, "imageUri.path?.isNotEmpty(): ${imageUri.path?.isNotEmpty()}")
    }

    fun sendImage(coroutinesErrorHandler: CoroutinesErrorHandler) =
        baseRequest(_placeResponse, coroutinesErrorHandler) {
            homeRepository.sendImage(testfile)
        }

    fun getPlanHistory(coroutinesErrorHandler: CoroutinesErrorHandler) =
        baseRequest(_planHistoryResponse, coroutinesErrorHandler) {
            homeRepository.getHistoryList()
        }

    fun getHistoryDetail(coroutinesErrorHandler: CoroutinesErrorHandler, id: String) =
        baseRequest(_historyDetailResponse, coroutinesErrorHandler) {
            homeRepository.getHistoryDetail(id)
        }

    fun resetDetail() {
        historyDetail = HistoryDetail()
    }
}