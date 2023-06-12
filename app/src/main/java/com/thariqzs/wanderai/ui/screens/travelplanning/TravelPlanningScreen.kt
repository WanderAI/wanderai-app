@file:OptIn(ExperimentalMaterial3Api::class)

package com.thariqzs.wanderai.ui.screens.travelplanning

import android.util.Range
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.util.toRange
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.screens.shared.components.CustomTextInput
import com.thariqzs.wanderai.ui.theme.BlueLight
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.BlueSky
import com.thariqzs.wanderai.ui.theme.BorderColor
import com.thariqzs.wanderai.ui.theme.Gray200
import com.thariqzs.wanderai.ui.theme.Gray300
import com.thariqzs.wanderai.ui.theme.Gray400
import com.thariqzs.wanderai.ui.theme.GreenNormal
import com.thariqzs.wanderai.ui.theme.PinkNormal
import com.thariqzs.wanderai.ui.theme.PlaceholderColor
import com.thariqzs.wanderai.ui.theme.UserBubbleBg
import com.thariqzs.wanderai.ui.theme.a
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h4
import com.thariqzs.wanderai.ui.theme.sh2
import java.time.LocalDate

@Composable
fun TravelPlanningScreen(navController: NavController) {
    TravelPlanningScreenBody(navController = navController)
}

@Composable
fun TravelPlanningScreenBody(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }
    var q by remember { mutableStateOf("") }
    Column(
        Modifier.fillMaxSize()
    ) {
        ScreenHeader(navController = navController)
        ChatContainer(onOpenDialog = {
            when (it) {
                1 -> showDialog = true
                2 -> showDialog2 = true
            }
        })
    }
    BottomActionButton(onPressBtn1 = { /*TODO*/ }) {
    }
    if (showDialog) {
        CustomDialog(q, onValueChange = { q = it }, showDialog, setShowDialog = {
            showDialog = it
        })
    }
    if (showDialog2) {
        DatePickerDialog(setShowDialog = {
            showDialog2 = it
        })
    }
}

@Composable
fun ScreenHeader(navController: NavController) {
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
            }
        }
    }
}

@Composable
fun ChatContainer(onOpenDialog: (Int) -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 92.dp)
            .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            DateDetail(date = "Wednesday, 24 June 2021")
            BotBubble(
                text = "Hello, I’m Travel-bot! \uD83D\uDC4B I’m your personal travel assistant!",
                withAvatar = true
            )
            BotBubble(
                text = "Kamu mau membuat rencana daengan kustom atau kami buatkan secara random?",
            )
            UserBubble(text = "Buat sendiri")
            BotBubble(
                text = "Kamu mau liburan kemana?",
                withAvatar = true
            )
            UserActionBubble(text = "Pilih destinasi liburan", onPressAction = { onOpenDialog(1) })
            BotBubble(
                text = "Liburannya mau mulai dari kapan nih?",
                withAvatar = true
            )
            UserActionBubble(
                text = "Pilih tanggal mulai liburan",
                onPressAction = { onOpenDialog(2) })
            UserActionBubble(text = "Beri deskripsi yang kamu inginkan", onPressAction = { onOpenDialog(1) })
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
                .clickable { onPressAction() }
                .padding(16.dp),
        ) {
            Text(text, style = b2, color = BlueNormal, modifier = Modifier.background(Color.White))
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun BottomActionButton(onPressBtn1: () -> Unit, onPressBtn2: () -> Unit) {
    Box(modifier = Modifier.fillMaxHeight()) {
        Column(Modifier.align(Alignment.BottomCenter)) {
            CustomTextInput(containerModifier = Modifier.background(BlueNormal).padding(vertical = 32.dp, horizontal = 16.dp), label = "test", value = "" , onValueChange = {}, noLabel = true)

//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(1.dp)
//                    .border(2.dp, Gray400)
//            )
//            Box(
//                Modifier
//
//                    .padding(horizontal = 16.dp, vertical = 24.dp)
//            ) {
//                Row(Modifier.align(Alignment.BottomCenter)) {
//                    Button(
//                        modifier = Modifier
//                            .height(44.dp)
//                            .weight(1f),
//                        shape = RoundedCornerShape(16.dp),
//                        border = BorderStroke(2.dp, BlueNormal),
//                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
//                        onClick = onPressBtn1
//                    ) {
//                        Text(
//                            text = "Save", style = h4, color = BlueNormal
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Button(
//                        modifier = Modifier
//                            .height(44.dp)
//                            .weight(1f),
//                        shape = RoundedCornerShape(16.dp),
//                        colors = ButtonDefaults.buttonColors(containerColor = BlueNormal),
//                        onClick = onPressBtn1
//                    ) {
//                        Text(
//                            text = "Generate", style = h4
//                        )
//                    }
//                }
//            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    q: String,
    onValueChange: (String) -> Unit,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
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
                            .clickable { setShowDialog(false) }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                BasicTextField(
                    value = q, onValueChange = { newText -> onValueChange(newText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    textStyle = b2,
                    decorationBox = @Composable { innerTextField ->
                        TextFieldDefaults.TextFieldDecorationBox(value = q,
                            innerTextField = innerTextField,
                            enabled = true,
                            singleLine = true,
                            interactionSource = interactionSource,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            visualTransformation = VisualTransformation.None,
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp),
                            placeholder = {
                                Text(
                                    text = "Cari destinasi liburan",
                                    color = PlaceholderColor
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_search),
                                    contentDescription = "ic_search"
                                )
                            })
                    },
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(
                    Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(24) {
                        var active by remember {
                            mutableStateOf(it % 2 == 0)
                        }
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .border(2.dp, BorderColor, RoundedCornerShape(12.dp))
                                .clip(
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { active = !active }
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
                                    if (active) {
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
                                Text("Yogyakarta", style = b2)
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
                var active by remember {
                    mutableStateOf(false)
                }
                Card(
                    Modifier
                        .fillMaxWidth()
                        .border(2.dp, BorderColor, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Row(
                        Modifier
                            .background(Color.White)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .size(20.dp)
                                .border(2.dp, BlueNormal, RoundedCornerShape(20.dp))
                                .clip(RoundedCornerShape(20.dp))
                        ) {
                            if (active) {
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
                        Text("Belum menemukan yang cocok. Tolong pilihkan untukku", style = b2)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = BlueNormal),
                ) {
                    Text("Selanjutnya", color = Color.White, style = b2)
                }
            }
        }
    }
}

@Composable
fun DatePickerDialog(setShowDialog: (Boolean) -> Unit) {
    val timeBoundary = LocalDate.now().let { now -> now..now.plusYears(2) }
    val selectedRange = remember {
        val default =
            LocalDate.now().let { time -> time.plusDays(0)..time.plusDays(1) }
        mutableStateOf(default.toRange())
    }
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
                    .clickable { setShowDialog(false) }
            )
        }
    }

    CalendarDialog(
        state = rememberUseCaseState(
            visible = true,
            true,
            onCloseRequest = { setShowDialog(false) }),
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            boundary = timeBoundary,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Period(
            selectedRange = selectedRange.value
        ) { startDate, endDate ->
            selectedRange.value = Range(startDate, endDate)
        },
        header = dialogHeader
    )
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun Preview() {
    TravelPlanningScreen(rememberNavController())
}
