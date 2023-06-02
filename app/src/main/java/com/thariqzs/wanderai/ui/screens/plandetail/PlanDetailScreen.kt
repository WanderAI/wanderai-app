package com.thariqzs.wanderai.ui.screens.plandetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.theme.a
import com.thariqzs.wanderai.ui.theme.b1
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h4

@Composable
fun PlanDetailScreen(navController: NavController) {
    PlanDetailBody(navController)
}

@Composable
fun PlanDetailBody(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.plan_detail_banner),
        contentDescription = "plan_detail_banner"
    )
    Column(
        Modifier.fillMaxSize()
    ) {
        PlanDetailScreenHeader(navController)
        PlanDetailScreenBody()
    }
}

@Composable
fun PlanDetailScreenHeader(navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(all = 12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = "ic_chevron_left",
            tint = Color.White,
            modifier = Modifier
                .size(52.dp)
                .clickable { navController.navigate(Routes.ListPlan) }
        )
        Text(text = "Plan Detail", style = h4, color = Color.White)
    }
}

@Composable
fun PlanDetailScreenBody() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 4.dp),

    ) {
        Text("Created on 28 May 2023", style = b2, color = Color.White)
        Spacer(modifier = Modifier.height(24.dp))
        Text("\uD83D\uDCCD Destination", style = h4)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Yogyakarta", style = b1)
        Spacer(modifier = Modifier.height(24.dp))
        Text("\uD83D\uDDD3️ Date", style = h4)
        Spacer(modifier = Modifier.height(4.dp))
        Text("22 - 26 May 2023", style = b1)
        Spacer(modifier = Modifier.height(24.dp))
        Text("\uD83D\uDCB5 Budget", style = h4)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Rp1.000.000 - Rp2.000.000", style = b1)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Description", style = h4)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec est orci, suscipit at est eu, porttitor rutrum mauris. Pellentesque condimentum tincidunt commodo. Praesent dapibus arcu vel vehicula pellentesque. Aliquam posuere nisi non blandit bibendum. Phasellus massa lectus, venenatis a arcu quis, euismod interdum mi. Phasellus in venenatis dolor. Curabitur tincidunt tempor imperdiet. Curabitur lectus diam, fringilla eu nibh sodales, euismod fringilla enim. Sed in nibh sed ipsum venenatis tristique. Integer porta aliquam leo ut pretium. Fusce et mattis metus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec est orci, suscipit at est eu, porttitor rutrum mauris. Pellentesque condimentum tincidunt commodo. Praesent dapibus arcu vel vehicula pellentesque. Aliquam posuere nisi non blandit bibendum. Phasellus massa lectus, venenatis a arcu quis, euismod interdum mi. Phasellus in venenatis dolor. Curabitur tincidunt tempor imperdiet. Curabitur lectus diam, fringilla eu nibh sodales, euismod fringilla enim. Sed in nibh sed ipsum venenatis tristique. Integer porta aliquam leo ut pretium. Fusce et mattis metus.",
            style = b1
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun Preview() {
    PlanDetailScreen(rememberNavController())
}
