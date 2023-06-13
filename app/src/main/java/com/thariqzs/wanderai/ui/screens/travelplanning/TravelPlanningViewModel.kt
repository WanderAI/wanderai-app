package com.thariqzs.wanderai.ui.screens.travelplanning

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.core.util.toRange
import androidx.lifecycle.viewModelScope
import com.thariqzs.wanderai.data.api.model.BudgetDetail
import com.thariqzs.wanderai.data.api.model.Chat
import com.thariqzs.wanderai.data.api.model.CityDetail
import com.thariqzs.wanderai.data.api.model.Recommendation
import com.thariqzs.wanderai.data.api.model.RequestUserAction
import com.thariqzs.wanderai.data.repository.TravelPlanningRepository
import com.thariqzs.wanderai.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TravelPlanningViewModel @Inject constructor(private val travelPlanningRepository: TravelPlanningRepository) :
    BaseViewModel() {

    val TAG = "tpvmthoriq"
    var showDialog by mutableStateOf(false)
    var showDialog2 by mutableStateOf(false)
    var showDialog3 by mutableStateOf(false)
    var chatList by mutableStateOf(listOf<Chat>())
    var descriptionQ by mutableStateOf("")
    var focusRequester by mutableStateOf(FocusRequester())
    var chatEnabled by mutableStateOf(false)

    val default = LocalDate.now().let { time -> time.plusDays(0)..time.plusDays(1) }
    var selectedRange by mutableStateOf(default.toRange())

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
    )

    val cityList: List<CityDetail> = listOf(
        CityDetail("Jakarta", 0),
        CityDetail("Bandung", 1),
        CityDetail("Yogyakarta", 2),
        CityDetail("Semarang", 3),
        CityDetail("Surabaya", 4)
    )
    var selectedCity by mutableStateOf(listOf<Int>())

    val budget: List<BudgetDetail> = listOf(
        BudgetDetail("< Rp500.000", 0),
        BudgetDetail("Rp500.000 - Rp1.000.000", 1),
        BudgetDetail("Rp1.000.000 - Rp2.000.000", 2),
        BudgetDetail("Rp2.000.000 - Rp3.500.000", 3),
        BudgetDetail("Rp3.500.000 - Rp5.000.000", 4),
        BudgetDetail("Rp5.000.000 - Rp7.000.000", 5),
        BudgetDetail("Rp7.000.000 - Rp10.000.000", 6),
        BudgetDetail("> Rp10.000.000", 7),
    )
    var selectedBudget by mutableStateOf(listOf<Int>())
    val chatDelay: Long = 300

    private val initChatFlow: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Hello, I’m Travel-bot! \uD83D\uDC4B I’m your personal travel assistant!"))
        delay(chatDelay)
        emit(Chat(false, "Kamu mau membuat rencana sendiri atau secara random?"))
        delay(chatDelay)
        emit(Chat(true, "Buat sendiri", actionType = 1))
        emit(Chat(true, "Buat secara random", actionType = 2))
    }

    private val chatFlow1: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Kamu mau liburan kemana?"))
        delay(chatDelay)
        emit(Chat(true, "Pilih destinasi liburan", actionType = 3))
    }

    private val chatFlow2: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Liburannya mau mulai dari kapan nih?"))
        delay(chatDelay)
        emit(Chat(true, "Pilih tanggal liburan", actionType = 4))
    }

    private val chatFlow3: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Berapa preferensi budget kamu?"))
        delay(chatDelay)
        emit(Chat(true, "Pilih perkiraan budget", actionType = 5))
    }

    private val chatFlow4: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Apa ada deskripsi yang mau ditambah sebelum aku berikan rekomendasi?"))
        delay(chatDelay)
        emit(Chat(true, "Klik dan ketik deskripsimu", actionType = 6))
        emit(Chat(true, "Tidak ada nih", actionType = 7))
    }

    private val chatFlow5: Flow<Chat> = flow {
        delay(chatDelay)
        emit(Chat(false, "Yeay travel plan berhasil dibuat!"))
        delay(chatDelay)
        emit(Chat(false, "", result = Recommendation("Yogyakarta", date = "22 - 26 May 2023")))
    }


    init {
        viewModelScope.launch {
            initChatFlow.collect {
                addChat(it)
            }
        }
    }

    fun addChat(chat: Chat) {
        chatList = chatList + chat
    }

    fun userResponse(id: Int) {
        when (id) {
            1 -> viewModelScope.launch {
                chatFlow1.collect {
                    addChat(it)
                }
            }

            3 -> {
                if (showDialog == true) {
                    showDialog = false
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
                        chatFlow2.collect {
                            addChat(it)
                        }
                    }
                } else {
                    showDialog = true
                }
            }

            4 -> {
                if (showDialog2 == true) {
                    showDialog2 = false
                    viewModelScope.launch {
                        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale("id"))

                        val dates = selectedRange.toString()
                            .removeSurrounding("[", "]")
                            .split(", ")
                            .map { LocalDate.parse(it).format(formatter) }

                        val formattedString = "Dari tanggal ${dates[0]} sampai ${dates[1]}"

                        addChat(Chat(true, formattedString))
                        delay(300)
                        chatFlow3.collect {
                            addChat(it)
                        }
                    }
                } else {
                    showDialog2 = true
                }
            }

            5 -> {
                if (showDialog3 == true) {
                    showDialog3 = false
                    viewModelScope.launch {
                        val selectedBudgets = budget.filter { selectedBudget.contains(it.id) }
                        val amounts = selectedBudgets.map { it.amount }

                        val formattedString = "Budget jalan-jalan berkisar ${amounts.joinToString(", ")}"

                        addChat(Chat(true, formattedString))
                        chatFlow4.collect {
                            addChat(it)
                        }
                    }
                } else {
                    showDialog3 = true
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
                    chatFlow5.collect {
                        addChat(it)
                    }
                }
            }

            8 -> {
                if (chatEnabled == true) {
                    addChat(Chat(true, descriptionQ))
                    descriptionQ = ""
                }
                chatEnabled = false
                viewModelScope.launch {
                    chatFlow5.collect {
                        addChat(it)
                    }
                }
            }

            else -> {}
        }
    }

    fun setCityActive(id: Int) {
        if (selectedCity.contains(id)) {
            selectedCity = selectedCity - id
        } else {
            selectedCity = selectedCity + id
        }
    }

    fun setBudgetActive(id: Int) {
        if (selectedBudget.contains(id)) {
            selectedBudget = selectedBudget - id
        } else {
            selectedBudget = selectedBudget + id
        }
    }
}