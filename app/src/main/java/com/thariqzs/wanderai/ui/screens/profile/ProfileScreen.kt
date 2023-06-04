package com.thariqzs.wanderai.ui.screens.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.theme.BlueLight
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.OrangeNormal
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h4

@Composable
fun ProfileScreen(navController: NavController) {
    ProfileBody(navController)
}

@Composable
fun ProfileBody(navController: NavController) {
    Column(
        Modifier.fillMaxSize()
    ) {
        ProfileScreenHeader(navController)
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ActionCard(icon = R.drawable.ic_settings, label = "Account Settings") {}
            ActionCard(icon = R.drawable.ic_lock, label = "Privacy Policy") {}
            ActionCard(icon = R.drawable.ic_sticky_note, label = "Terms and Condition") {}
            ActionCard(icon = R.drawable.ic_help, label = "Help Center") {}
        }
    }
}

@Composable
fun ProfileScreenHeader(navController: NavController) {
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
                .clickable { navController.navigate(Routes.ListPlan) }
        )
        Text(text = "Profile", style = h4)
    }
}

@Composable
fun ActionCard(icon: Int, label: String, navigateTo: () -> Unit?) {
    val blueLightWithOpacity = BlueLight.copy(alpha = 0.2f)

    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, blueLightWithOpacity, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "opt1",
                tint = OrangeNormal
            )
            Text(label, style = b2)
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            tint = BlueNormal,
            contentDescription = "ic_chevron_right"
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun Preview() {
    ProfileScreen(rememberNavController())
}