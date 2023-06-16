package com.thariqzs.wanderai.ui.screens.travelplanning

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.core.util.toRange
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thariqzs.wanderai.data.api.PreferenceRequest
import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.data.api.model.BudgetDetail
import com.thariqzs.wanderai.data.api.model.Chat
import com.thariqzs.wanderai.data.api.model.CityDetail
import com.thariqzs.wanderai.data.api.model.DefaultResponse
import com.thariqzs.wanderai.data.api.model.History
import com.thariqzs.wanderai.data.api.model.Recommendation
import com.thariqzs.wanderai.data.api.model.RequestUserAction
import com.thariqzs.wanderai.data.repository.TravelPlanningRepository
import com.thariqzs.wanderai.utils.BaseViewModel
import com.thariqzs.wanderai.utils.CoroutinesErrorHandler
import com.thariqzs.wanderai.utils.convertDateRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TravelPlanningViewModel @Inject constructor(private val travelPlanningRepository: TravelPlanningRepository) :
    BaseViewModel() {

    val TAG = "tpvmthoriq"
    val default = LocalDate.now().let { time -> time.plusDays(0)..time.plusDays(1) }
    var showDialogDestination by mutableStateOf(false)
    var showDialogDate by mutableStateOf(false)
    var showDialogBudget by mutableStateOf(false)
    var showDialogNumOfUser by mutableStateOf(false)
    var chatList by mutableStateOf(listOf<Chat>())
    var descriptionQ by mutableStateOf("")
    var focusRequester by mutableStateOf(FocusRequester())
    var chatEnabled by mutableStateOf(false)
    var numOfUser by mutableStateOf("")
    var selectedCity by mutableStateOf(listOf<Int>())
    var selectedBudget by mutableStateOf(listOf<Int>())
    var selectedRange by mutableStateOf(default.toRange())

    var requestResult by mutableStateOf(History())

    private var _planResponse = MutableLiveData<ApiResponse<DefaultResponse<History>>>()
    var planResponse = _planResponse

    val actionList: List<RequestUserAction> = listOf(
        RequestUserAction("", 0),
        RequestUserAction("Pilih Sendiri", 1),
        RequestUserAction("Random", 2),
        RequestUserAction("Dialog Destinasi", 3),
        RequestUserAction("Dialog Tanggal", 4),
        RequestUserAction("Dialog Budget", 5),
        RequestUserAction("Tulis Deskripsi", 6),
        RequestUserAction("No Description", 7),
        RequestUserAction("Kirim Deskripsi", 8),
        RequestUserAction("Dialog Number of User", 9),
    )

    val cityList: List<CityDetail> = listOf(
        CityDetail("Jakarta", 0),
        CityDetail("Bandung", 1),
        CityDetail("Yogyakarta", 2),
        CityDetail("Semarang", 3),
        CityDetail("Surabaya", 4)
    )

    val budget: List<BudgetDetail> = listOf(
        BudgetDetail("Ingin yang paling murah ", 1),
        BudgetDetail("Harganya biasa-biasa aja", 2),
        BudgetDetail("Harga mahal bisa diurus ", 3),
        BudgetDetail("Budget bukanlah batasan :D", 4),
    )

    val chatDelay: Long = 300

    private val chatFlowPreference: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Kamu mau membuat rencana sendiri atau secara random?"))
        delay(chatDelay)
        emit(Chat(true, "Buat sendiri", actionType = 1))
        emit(Chat(true, "Buat secara random", actionType = 2))
    }

    private val chatFlowDestination: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Kamu mau liburan kemana?"))
        delay(chatDelay)
        emit(Chat(true, "Pilih destinasi liburan", actionType = 3))
    }

    private val chatFlowNumOfUser: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Ada berapa orang yang akan ikut?"))
        delay(chatDelay)
        emit(Chat(true, "Ketik jumlah orang", actionType = 9))
    }

    private val chatFlowDate: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Halo, Aku Travel-bot! \uD83D\uDC4B Aku adalah asisten travel kamu!"))
        delay(chatDelay)
        emit(Chat(false, "Liburannya mau mulai dari kapan nih?"))
        delay(chatDelay)
        emit(Chat(true, "Pilih tanggal liburan", actionType = 4))
    }

    private val chatFlowBudget: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Berapa preferensi budget kamu?"))
        delay(chatDelay)
        emit(Chat(true, "Pilih preferensi budget", actionType = 5))
    }

    private val chatFlowDescription: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Apa ada deskripsi yang mau ditambah sebelum aku berikan rekomendasi?"))
        delay(chatDelay)
        emit(Chat(true, "Ketik deskripsimu", actionType = 6))
        emit(Chat(true, "Tidak ada nih", actionType = 7))
    }

    private val chatFlowResult: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Yeay travel plan berhasil dibuat!"))
        delay(chatDelay)
        emit(
            Chat(
                false,
                "",
                result = Recommendation(
                    requestResult.city.toString(),
                    date = requestResult.date_start
                )
            )
        )
    }


    init {
        viewModelScope.launch {
            chatFlowDate.collect {
                addChat(it)
            }
        }
//        viewModelScope.launch {
//            chatFlowNumOfUser.collect {
//                addChat(it)
//            }
//        }
    }

    fun addChat(chat: Chat) {
        chatList = chatList + chat
    }

    fun userResponse(id: Int) {
        when (id) {
            1 -> viewModelScope.launch {
                chatFlowDestination.collect {
                    addChat(it)
                }
            }

            2 -> {
                viewModelScope.launch {
                    chatFlowResult.collect {
                        addChat(it)
                    }
                }
            }

            3 -> {

                if (showDialogDestination) {
                    showDialogDestination = false
                    viewModelScope.launch {
                        val selectedCities = cityList.filter { selectedCity.contains(it.id) }
                        val selectedCitiesNames = selectedCities.joinToString(", ") { it.cityName }

                        val recommendedCities: String = when {
                            selectedCities.size == 1 -> "Kota ${selectedCitiesNames}"
                            selectedCities.size == 2 -> "Kota ${selectedCities[0].cityName} dan ${selectedCities[1].cityName}"
                            else -> {
                                val lastCityName = selectedCities.last().cityName
                                val otherCitiesNames =
                                    selectedCities.dropLast(1).joinToString(", ") { it.cityName }
                                "Kota ${otherCitiesNames}, dan $lastCityName"
                            }
                        }

                        val recommendationMessage = "Carikan rekomendasi untuk $recommendedCities"

                        addChat(Chat(true, recommendationMessage))
                        delay(300)
                        chatFlowNumOfUser.collect {
                            addChat(it)
                        }
                    }
                } else {
                    showDialogDestination = true
                }
            }

            4 -> {
                if (showDialogDate) {
                    showDialogDate = false
                    viewModelScope.launch {
                        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                            .withLocale(Locale("id"))

                        val dates = selectedRange.toString()
                            .removeSurrounding("[", "]")
                            .split(", ")
                            .map { LocalDate.parse(it).format(formatter) }

                        val formattedString = "Dari tanggal ${dates[0]} sampai ${dates[1]}"

                        addChat(Chat(true, formattedString))
                        delay(300)
                        chatFlowPreference.collect {
                            addChat(it)
                        }
                    }
                } else {
                    showDialogDate = true
                }
            }

            5 -> {
                if (showDialogBudget) {
                    showDialogBudget = false
                    viewModelScope.launch {
                        val selectedBudgets = budget.filter { selectedBudget.contains(it.id) }
                        val amounts = selectedBudgets.map { it.amount }

                        val formattedString =
                            "Budget jalan-jalan berkisar ${amounts.joinToString(", ")}"

                        addChat(Chat(true, formattedString))
                        chatFlowDescription.collect {
                            addChat(it)
                        }
                    }
                } else {
                    showDialogBudget = true
                }
            }

            6 -> {
                chatEnabled = true
                viewModelScope.launch {
                    flow<Int> {
                        delay(300)
                        focusRequester.requestFocus()
                    }.collect()
                }
            }

            7 -> {
                viewModelScope.launch {
                    chatFlowResult.collect {
                        addChat(it)
                    }
                }
            }

            8 -> {
                if (chatEnabled) {
                    addChat(Chat(true, descriptionQ))
                    descriptionQ = ""
                }
                chatEnabled = false
                viewModelScope.launch {
                    chatFlowResult.collect {
                        addChat(it)
                    }
                }
            }

            9 -> {
                if (showDialogNumOfUser) {
                    showDialogNumOfUser = false
                    viewModelScope.launch {
                        addChat(Chat(true, "Saya bersama ${numOfUser} teman saya"))
                        chatFlowBudget.collect {
                            addChat(it)
                        }
                    }
                } else {
                    showDialogNumOfUser = true
                }
            }

            else -> {}
        }
    }

    fun setCityActive(id: Int) {
        if (selectedCity.contains(id)) {
            selectedCity = selectedCity - id
        } else {
            selectedCity = arrayListOf(id)
        }
    }

    fun setBudgetActive(id: Int) {
        if (selectedBudget.contains(id)) {
            selectedBudget = selectedBudget - id
        } else {
            selectedBudget = arrayListOf(id)
        }
    }

    fun requestRandom(coroutinesErrorHandler: CoroutinesErrorHandler) =
        baseRequest(_planResponse, coroutinesErrorHandler) {
            val payload = convertDateRange(selectedRange.toString())
//            val payload = RandomRequest()
            travelPlanningRepository.requestRandom(payload)
        }

    fun requestWithPreference(coroutinesErrorHandler: CoroutinesErrorHandler) =
        baseRequest(_planResponse, coroutinesErrorHandler) {
            Log.d(TAG, "request: tests")
            val date = convertDateRange(selectedRange.toString())
            val payload = PreferenceRequest(
                descriptionQ,
                cityList[selectedCity[0]].cityName,
                date.day_start,
                date.day_end,
                numOfUser.toInt(),
                budget[selectedBudget[0]].id
            )
            Log.d(TAG, "payload: $payload")
            travelPlanningRepository.requestWithPreference(payload)
        }

    fun printRange() {
        Log.d(
            TAG,
            "convertDateRange(selectedRange.toString()): ${convertDateRange(selectedRange.toString())}"
        )

    }

    fun resetChat() {
        numOfUser = ""
        selectedCity = listOf<Int>()
        selectedBudget = listOf<Int>()
        selectedRange = default.toRange()
        descriptionQ = ""
        chatList = listOf<Chat>()
        viewModelScope.launch {
            chatFlowDate.collect {
                addChat(it)
            }
        }
    }
}