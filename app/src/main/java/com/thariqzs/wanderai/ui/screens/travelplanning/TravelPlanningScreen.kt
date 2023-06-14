@file:OptIn(ExperimentalMaterial3Api::class)

package com.thariqzs.wanderai.ui.screens.travelplanning

import android.util.Log
import android.util.Range
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.data.api.model.BudgetDetail
import com.thariqzs.wanderai.data.api.model.Chat
import com.thariqzs.wanderai.data.api.model.CityDetail
import com.thariqzs.wanderai.data.api.model.History
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.screens.listplan.PlanCard
import com.thariqzs.wanderai.ui.screens.shared.components.CustomTextInput
import com.thariqzs.wanderai.ui.screens.shared.components.CustomTextInputChat
import com.thariqzs.wanderai.ui.theme.BlueLight
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.BlueSky
import com.thariqzs.wanderai.ui.theme.BorderColor
import com.thariqzs.wanderai.ui.theme.Gray200
import com.thariqzs.wanderai.ui.theme.Gray300
import com.thariqzs.wanderai.ui.theme.Gray400
import com.thariqzs.wanderai.ui.theme.GreenNormal
import com.thariqzs.wanderai.ui.theme.PinkNormal
import com.thariqzs.wanderai.ui.theme.UserBubbleBg
import com.thariqzs.wanderai.ui.theme.a
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h4
import com.thariqzs.wanderai.ui.theme.sh2
import com.thariqzs.wanderai.utils.CoroutinesErrorHandler
import java.time.LocalDate

@Composable
fun TravelPlanningScreen(navController: NavController, tpvm: TravelPlanningViewModel) {
    val planRes by tpvm.planResponse.observeAsState()
    val context = LocalContext.current
    val TAG = "tpsthoriq"

    when (val response = planRes) {
        is ApiResponse.Success -> {
            val data = response.data.data
            if (data != null) {
                tpvm.requestResult = History(data.doc_id, data.city, data.date_start, data.date_end)
            }
        }

        is ApiResponse.Failure -> {
            val errorMessage = response.errorMessage
            Toast.makeText(context, "${errorMessage}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "failure: ${errorMessage}")
        }

        is ApiResponse.Loading -> {
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "loading... ")
        }

        else -> {
//            Log.d(TAG, "else: ${response}")
        }
    }

    TravelPlanningScreenBody(navController = navController, tpvm)
}

@Composable
fun TravelPlanningScreenBody(navController: NavController, tpvm: TravelPlanningViewModel) {
    val TAG = "tpsthoriq"
    var q by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize()
    ) {
        ScreenHeader(navController = navController) { tpvm.resetChat() }
        ChatContainer(tpvm, navController)
    }
    BottomActionButton(
        descText = tpvm.descriptionQ,
        onChangeText = { text -> tpvm.descriptionQ = text },
        focusRequester = tpvm.focusRequester,
        onSendChat = { tpvm.userResponse(8) },
        enabled = tpvm.chatEnabled
    )

    if (tpvm.showDialogDestination) {
        CustomDialog(
            q,
            onValueChange = { q = it },
            tpvm.showDialogDestination,
            setShowDialog = {
                tpvm.showDialogDestination = it
            },
            cityList = tpvm.cityList,
            onClickItem = { id -> tpvm.setCityActive(id) },
            selectedCityList = tpvm.selectedCity,
            onSaveSelection = { tpvm.userResponse(3) })
    }

    if (tpvm.showDialogDate) {
        DatePickerDialog(tpvm.showDialogDate, setShowDialog = {
            tpvm.showDialogDate = it
        }, onSelectDate = {
            tpvm.userResponse(4)
        },
            selectedRange = tpvm.selectedRange,
            setSelectedRange = { tpvm.selectedRange = it }
        )
    }

    if (tpvm.showDialogBudget) {
        CustomDialogBudget(
            q,
            onValueChange = { q = it },
            tpvm.showDialogBudget,
            setShowDialog = {
                tpvm.showDialogBudget = it
            },
            budgetList = tpvm.budget,
            onClickItem = { id -> tpvm.setBudgetActive(id) },
            selectedBudgetList = tpvm.selectedBudget,
            onSaveSelection = { tpvm.userResponse(5) })
    }

    if (tpvm.showDialogNumOfUser) {
        CustomDialogNumOfPerson(
            value = tpvm.numOfUser,
            onValueChange = {
                Log.d(TAG, "it: $it")
                tpvm.numOfUser = it
            },
            showDialog = tpvm.showDialogNumOfUser,
            setShowDialog = { tpvm.showDialogNumOfUser = it },
            onSaveSelection = { tpvm.userResponse(9) })
    }
}

@Composable
fun ScreenHeader(navController: NavController, resetChat: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(all = 12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = "ic_chevron_left",
            tint = BlueNormal,
            modifier = Modifier
                .size(52.dp)
                .clickable { navController.navigate(Routes.Home) }
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(60.dp)
                .background(BlueSky, RoundedCornerShape(30.dp))
                .padding(bottom = 4.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_robot),
                contentDescription = "ic_user_circle",
                modifier = Modifier
                    .align(
                        Alignment.Center
                    )
                    .size(42.dp),
            )
        }
        Column(
            Modifier
                .weight(1f)
        ) {
            Text(text = "Travel-bot", style = sh2)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(GreenNormal, RoundedCornerShape(40.dp))
                )
                Text(
                    text = "Always active",
                    style = b2,
                    modifier = Modifier.padding(start = 4.dp),
                    color = Gray400
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .width(92.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, BlueNormal),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    onClick = { resetChat() }
                ) {
                    Text(
                        text = "Reset", style = b2, color = BlueNormal
                    )
                }
            }
        }
    }
}

@Composable
fun ChatContainer(tpvm: TravelPlanningViewModel, navController: NavController) {
    val listState = rememberLazyListState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    LaunchedEffect(lifecycle, tpvm.chatList.size) {
        if (tpvm.chatList.isNotEmpty()) {
            val lastIndex = tpvm.chatList.lastIndex
            val scrollToBottom = listState.layoutInfo.visibleItemsInfo
                .lastOrNull()?.index != lastIndex

            if (scrollToBottom) {
                listState.scrollToItem(lastIndex)
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(bottom = 92.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (tpvm.chatList.isNotEmpty()) {
            itemsIndexed(tpvm.chatList) { index, chat ->
//            DateDetail(date = "Wednesday, 24 June 2021")
                val previousChat = tpvm.chatList.getOrNull(index - 1)
                val withAvatar = previousChat?.isUser != false
                val text = chat.text

                if (chat.isUser == false) {
                    if (chat.result != null) {
                        Box(Modifier.padding(start = 46.dp, bottom = 8.dp)) {
                            PlanCard(
                                navigateTo = { navController.navigate("plan_detail/${tpvm.requestResult.doc_id}") },
                                item = tpvm.requestResult
                            )
                        }
                    } else {
                        BotBubble(
                            text = text,
                            withAvatar = withAvatar
                        )
                    }
                } else {
                    if (chat.actionType!! > 0) {
                        UserActionBubble(
                            text = text,
                            onPressAction = {
                                if (chat.actionType == 2) {
                                    tpvm.requestRandom(object : CoroutinesErrorHandler {
                                        override fun onError(message: String) {
                                            Log.d("asthoriq login", "onError: $message")
                                        }
                                    })
                                } else if (chat.actionType == 7 || chat.actionType == 8) {
                                    tpvm.requestWithPreference(object : CoroutinesErrorHandler {
                                        override fun onError(message: String) {
                                            Log.d("asthoriq login", "onError: $message")
                                        }
                                    })
                                }
                                tpvm.userResponse(chat.actionType)
                            })
                    } else {
                        UserBubble(text = text)
                    }
                }
            }
        }
    }
}

@Composable
fun DateDetail(date: String) {
    Spacer(modifier = Modifier.height(8.dp))
    Box(
        Modifier
            .background(Gray200, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(date, style = a, color = Gray300)
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun BotBubble(text: String, withAvatar: Boolean? = false) {
    var bgColor = when (withAvatar) {
        true -> BlueSky
        else -> Color.Transparent
    }

    Row(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(bgColor, RoundedCornerShape(30.dp))
                .padding(bottom = 4.dp),
        ) {
            if (withAvatar == true) {
                Image(
                    painter = painterResource(R.drawable.ic_robot),
                    contentDescription = "ic_user_circle",
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                        .size(30.dp),
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .background(
                    UserBubbleBg,
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 24.dp,
                        bottomEnd = 24.dp,
                        bottomStart = 24.dp
                    )
                )
                .padding(all = 16.dp)
        ) {
            Text(text, style = b2)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun UserBubble(text: String) {
    val blueLightWithOpacity = BlueLight.copy(alpha = 0.25f)

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

        Box(
            Modifier
                .background(blueLightWithOpacity, RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Text(text, style = b2)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun UserActionBubble(text: String, onPressAction: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Card(
            Modifier
                .border(BorderStroke(1.dp, BlueNormal), RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .clickable { onPressAction() }
                .padding(16.dp),
        ) {
            Text(text, style = b2, color = BlueNormal, modifier = Modifier.background(Color.White))
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun BottomActionButton(
    descText: String,
    onChangeText: (String) -> Unit,
    focusRequester: FocusRequester,
    onSendChat: () -> Unit,
    enabled: Boolean = false
) {
    Box(modifier = Modifier.fillMaxHeight()) {
        Column(Modifier.align(Alignment.BottomCenter)) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .border(2.dp, Gray400)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            ) {
                CustomTextInputChat(
                    containerModifier = Modifier,
                    label = "",
                    value = descText,
                    onValueChange = { text -> onChangeText(text) },
                    noLabel = true,
                    focusRequester = focusRequester,
                    onSendChat = { onSendChat() },
                    enabled = enabled,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    q: String,
    onValueChange: (String) -> Unit,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    cityList: List<CityDetail>,
    selectedCityList: List<Int>,
    onClickItem: (Int) -> Unit,
    onSaveSelection: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Dialog(onDismissRequest = {
        setShowDialog(false)
    }) {
        Surface(
            Modifier
                .fillMaxSize()
                .padding(vertical = 100.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Pilih destinasi Liburan", style = h4)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "ic_close",
                        tint = PinkNormal,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable {
                                Log.d("thoriqtestdialog", "CustomDialog2222: test")
                                setShowDialog(false)
                            }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(
                    Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(cityList) {
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .border(2.dp, BorderColor, RoundedCornerShape(12.dp))
                                .clip(
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { onClickItem(it.id) }
                                .padding(horizontal = 12.dp, vertical = 16.dp)
                        ) {
                            Row(
                                Modifier
                                    .background(Color.White)
                                    .fillMaxWidth()
                            ) {
                                Box(
                                    Modifier
                                        .size(20.dp)
                                        .border(2.dp, BlueNormal, RoundedCornerShape(20.dp))
                                        .clip(RoundedCornerShape(20.dp))
                                ) {
                                    if (selectedCityList.contains(it.id)) {
                                        Box(
                                            Modifier
                                                .size(12.dp)
                                                .background(BlueNormal, RoundedCornerShape(20.dp))
                                                .clip(RoundedCornerShape(20.dp))
                                                .align(Alignment.Center)
                                        ) {

                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(it.cityName, style = b2)
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(2.dp)
                        .border(1.dp, Gray300)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSaveSelection() },
                    colors = ButtonDefaults.buttonColors(containerColor = BlueNormal),
                ) {
                    Text("Selanjutnya", color = Color.White, style = b2)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    visible: Boolean,
    setShowDialog: (Boolean) -> Unit,
    onSelectDate: () -> Unit,
    selectedRange: Range<LocalDate>,
    setSelectedRange: (Range<LocalDate>) -> Unit
) {
    val timeBoundary = LocalDate.now().let { now -> now..now.plusYears(2) }

    val dialogHeader = Header.Custom {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Pilih destinasi Liburan", style = h4)
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "ic_close",
                tint = PinkNormal,
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        Log.d("closegaksavethoriq", "DatePickerDialog: ")
                        setShowDialog(false)
                    }
            )
        }
    }

    CalendarDialog(
        state = rememberUseCaseState(
            visible = visible,
            true,
            onFinishedRequest = {
                onSelectDate()
            },
            onDismissRequest = {
                setShowDialog(false)
            },
        ),
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            boundary = timeBoundary,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Period(
            selectedRange = selectedRange
        ) { startDate, endDate ->
            setSelectedRange(Range(startDate, endDate))
        },
        header = dialogHeader
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialogBudget(
    q: String,
    onValueChange: (String) -> Unit,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    budgetList: List<BudgetDetail>,
    selectedBudgetList: List<Int>,
    onClickItem: (Int) -> Unit,
    onSaveSelection: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Dialog(onDismissRequest = {
        Log.d("thoriqtestdialog", "CustomDialog: test")
        setShowDialog(false)
    }) {
        Surface(
            Modifier
                .fillMaxSize()
                .padding(vertical = 100.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Pilih destinasi Liburan", style = h4)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "ic_close",
                        tint = PinkNormal,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable {
                                setShowDialog(false)
                            }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(
                    Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(budgetList) {
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .border(2.dp, BorderColor, RoundedCornerShape(12.dp))
                                .clip(
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { onClickItem(it.id) }
                                .padding(horizontal = 12.dp, vertical = 16.dp)
                        ) {
                            Row(
                                Modifier
                                    .background(Color.White)
                                    .fillMaxWidth()
                            ) {
                                Box(
                                    Modifier
                                        .size(20.dp)
                                        .border(2.dp, BlueNormal, RoundedCornerShape(20.dp))
                                        .clip(RoundedCornerShape(20.dp))
                                ) {
                                    if (selectedBudgetList.contains(it.id)) {
                                        Box(
                                            Modifier
                                                .size(12.dp)
                                                .background(BlueNormal, RoundedCornerShape(20.dp))
                                                .clip(RoundedCornerShape(20.dp))
                                                .align(Alignment.Center)
                                        ) {

                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(it.amount, style = b2)
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(2.dp)
                        .border(1.dp, Gray300)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSaveSelection() },
                    colors = ButtonDefaults.buttonColors(containerColor = BlueNormal),
                ) {
                    Text("Selanjutnya", color = Color.White, style = b2)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialogNumOfPerson(
    value: String,
    onValueChange: (String) -> Unit,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    onSaveSelection: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Dialog(onDismissRequest = {
        setShowDialog(false)
    }) {
        Surface(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 100.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Jumlah Orang", style = h4)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "ic_close",
                        tint = PinkNormal,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable {
                                setShowDialog(false)
                            }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextInput(
                    containerModifier = Modifier.background(Color.White),
                    label = "",
                    value = value,
                    onValueChange = { onValueChange(it) },
                    noLabel = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(2.dp)
                        .border(1.dp, Gray300)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSaveSelection() },
                    colors = ButtonDefaults.buttonColors(containerColor = BlueNormal),
                ) {
                    Text("Selanjutnya", color = Color.White, style = b2)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    CustomDialogNumOfPerson(
        "2", {},
        true,
        {},
        {},
    )
}
