package com.thariqzs.wanderai.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.screens.auth.AuthScreen
import com.thariqzs.wanderai.ui.theme.BlueLight
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.BlueOld
import com.thariqzs.wanderai.ui.theme.OrangeLight
import com.thariqzs.wanderai.ui.theme.OrangeNormal
import com.thariqzs.wanderai.ui.theme.a
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h3
import com.thariqzs.wanderai.ui.theme.h4
import com.thariqzs.wanderai.ui.theme.sh2

@Composable
fun HomeScreen(navController: NavController) {
    HomeScreenBody(navController = navController)
}

@Composable
fun HomeScreenBody(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .drawWithContent {
                drawContent()
            }
    ) {

        Header(name = "Rey", navController = navController)
        Body(navController = navController)
        ListPlan()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_bottom_van),
            contentDescription = "ic_bottom_van",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(280.dp)
                .align(Alignment.BottomEnd)
                ,

            )
    }
}

@Composable
fun Header(name: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text("WanderAI", style = a, color = BlueNormal)
            Text("\uD83D\uDC4B Halo, $name!", style = h4)
        }
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(color = BlueNormal, shape = RoundedCornerShape(20.dp))
                .clickable { navController.navigate(Routes.Auth) },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user_circle),
                contentDescription = "ic_user_circle",
                tint = Color.White,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}

@Composable
fun Body(navController: NavController) {
    Column(
        modifier = Modifier.padding(all = 16.dp)
    ) {
        FeatureCard(
            icon = "✈️",
            label = "Travel\n" +
                    "Planning",
            "Travel-bot siap membantu untuk membuat rencana yang kamu mau!",
            image = R.drawable.ic_robot,
            btnColor = BlueNormal,
            handleNavigate = {navController.navigate(Routes.TravelPlan)}
        )
        Spacer(modifier = Modifier.height(16.dp))
        FeatureCard(
            icon = "\uD83D\uDE82",
            label = "Travel\n" +
                    "Recognition",
            "Buat rencana perjalanan sesuai keinginanmu secara otomatis!",
            image = R.drawable.ic_phone,
            btnColor = OrangeNormal
        , handleNavigate = {navController.navigate(Routes.TravelPlan)}
        )
    }
}

@Composable
fun FeatureCard(icon: String, label: String, description: String, image: Int, btnColor: Color, handleNavigate: () -> Unit) {
    val blueLightWithOpacity = BlueLight.copy(alpha = 0.2f)
    val orangeLightWithOpacity = OrangeLight.copy(alpha = 0.2f)

    val backgroundColor = if (btnColor == BlueNormal) {
        blueLightWithOpacity
    } else {
        orangeLightWithOpacity
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(all = 16.dp)
            .height(IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(icon, style = h3, modifier = Modifier.padding(bottom = 8.dp))
            Text(
                label, style = h3, color = btnColor, modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(description, style = a)
        }
        Spacer(modifier = Modifier.width(30.dp))
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .size(85.dp)
                    .background(Color.White, RoundedCornerShape(60.dp))
                    .padding(bottom = 4.dp),
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = "ic_user_circle",
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                        .size(60.dp),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(btnColor, RoundedCornerShape(8.dp))
                    .clickable { handleNavigate() }

            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = "ic_chevron_right",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(32.dp)
                )
            }
        }
    }
}

@Composable
fun ListPlan() {
    val blueLightWithOpacity = BlueLight.copy(alpha = 0.2f)

    Column(
        modifier = Modifier.background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("List Plan", style = h4)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Lihat Selengkapnya", style = a, color = BlueNormal)
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = "ic_chevron_right",
                    tint = BlueNormal,
                    modifier = Modifier
                        .size(16.dp)
                )
            }
        }
        LazyRow(
            modifier = Modifier.background(Color.Transparent),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(5) {
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(24.dp))
                        .border(
                            BorderStroke(1.dp, blueLightWithOpacity),
                            RoundedCornerShape(24.dp)
                        )
                        .padding(24.dp)
                ) {
                    Column() {
                        Text("Bandung", style = sh2)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row() {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendar),
                                contentDescription = "ic_calendar",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("24 Mei 2023", style = b2)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 380)
@Composable
fun Preview() {
    HomeScreen(rememberNavController())
}
