package com.thariqzs.wanderai.ui.screens.travelplanning

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.ui.screens.home.HomeScreen
import com.thariqzs.wanderai.ui.theme.BlueLight
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.BlueSky
import com.thariqzs.wanderai.ui.theme.Gray200
import com.thariqzs.wanderai.ui.theme.Gray300
import com.thariqzs.wanderai.ui.theme.Gray400
import com.thariqzs.wanderai.ui.theme.GreenNormal
import com.thariqzs.wanderai.ui.theme.UserBubbleBg
import com.thariqzs.wanderai.ui.theme.a
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h4
import com.thariqzs.wanderai.ui.theme.sh2

@Composable
fun TravelPlanningScreen(navController: NavController) {
    TravelPlanningScreenBody(navController = navController)
}

@Composable
fun TravelPlanningScreenBody(navController: NavController) {
    Column(
        Modifier.fillMaxSize()
    ) {
        Header()
        ChatContainer()
    }
    BottomActionButton(onPressBtn1 = { /*TODO*/ }) {
    }
}

@Composable
fun Header() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(all = 12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = "ic_chevron_left",
            tint = BlueNormal,
            modifier = Modifier.size(52.dp)
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
                Text(text = "Always active", style = b2, modifier = Modifier.padding(start = 4.dp))
            }
        }
    }
}

@Composable
fun ChatContainer() {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 92.dp)
            .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(10) {

            DateDetail(date = "Wednesday, 24 June 2021")
            UserBubble(
                text = "Hello, I’m Travel-bot! \uD83D\uDC4B I’m your personal travel assistant!",
                withAvatar = true
            )
            UserBubble(
                text = "Kamu mau membuat rencana daengan kustom atau kami buatkan secara random?",
            )
            PeerBubble(text = "Buat sendiri")
            UserBubble(
                text = "Kamu mau liburan kemana?",
                withAvatar = true
            )
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
fun UserBubble(text: String, withAvatar: Boolean? = false) {
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
fun PeerBubble(text: String) {
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
fun BottomActionButton(onPressBtn1: () -> Unit, onPressBtn2: () -> Unit) {
    Box(modifier = Modifier.fillMaxHeight()) {
        Column(Modifier.align(Alignment.BottomCenter)) {
            

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .border(2.dp, Gray400))
        Box(
            Modifier

                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Row(Modifier.align(Alignment.BottomCenter)) {
                Button(
                    modifier = Modifier
                        .height(44.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, BlueNormal),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    onClick = onPressBtn1
                ) {
                    Text(
                        text = "Save", style = h4, color = BlueNormal
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier
                        .height(44.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BlueNormal),
                    onClick = onPressBtn1
                ) {
                    Text(
                        text = "Generate", style = h4
                    )
                }
            }
        }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun Preview() {
    TravelPlanningScreen(rememberNavController())
}
