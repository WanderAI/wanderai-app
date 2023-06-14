package com.thariqzs.wanderai.ui.screens.listplan

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.data.api.model.History
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.screens.home.HomeViewModel
import com.thariqzs.wanderai.ui.screens.travelplanning.TravelPlanningViewModel
import com.thariqzs.wanderai.ui.theme.BlueLight
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.OrangeNormal
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h4
import com.thariqzs.wanderai.ui.theme.sh2

@Composable
fun ListPlanScreen(navController: NavController, hvm: HomeViewModel) {
    ListPlanScreenBody(navController = navController, hvm)
}

@Composable
fun ListPlanScreenBody(navController: NavController, hvm: HomeViewModel) {
    Column(
        Modifier.fillMaxSize()
    ) {
        ScreenHeader(navController)
        LazyColumn(Modifier.padding(horizontal = 36.dp)) {
            if (hvm.history.isNotEmpty()) {
                items(hvm.history) {
                    PlanCard(navigateTo = { navController.navigate("plan_detail/${it.doc_id}") }, item = it)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
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
        Text(text = "List Plan", style = h4)
    }
}

@Composable
fun PlanCard(navigateTo: () -> Unit, item: History? = History()) {
    val blueLightWithOpacity = BlueLight.copy(alpha = 0.2f)

    Card(
        Modifier
            .border(1.dp, blueLightWithOpacity, RoundedCornerShape(24.dp))
            .background(Color.White, RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Row(Modifier.background(Color.White), verticalAlignment = Alignment.CenterVertically) {
            Column() {
                Log.d("lpsthoriq", "item?.date_start: ${item?.date_start}")
                Text(item?.city ?: "Not Found", style = sh2)
                Spacer(modifier = Modifier.height(12.dp))
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "ic_calendar",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(item?.date_start ?: "-", style = b2)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(OrangeNormal, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { navigateTo() }

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
//
//@Preview(showBackground = true, widthDp = 360)
//@Composable
//fun Preview() {
//    ListPlanScreen(rememberNavController())
//}
