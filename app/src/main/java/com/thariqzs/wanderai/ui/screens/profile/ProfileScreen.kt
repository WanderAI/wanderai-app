package com.thariqzs.wanderai.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.theme.BlueLight
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.Gray200
import com.thariqzs.wanderai.ui.theme.Gray500
import com.thariqzs.wanderai.ui.theme.Gray600
import com.thariqzs.wanderai.ui.theme.OrangeNormal
import com.thariqzs.wanderai.ui.theme.RedNormal
import com.thariqzs.wanderai.ui.theme.b1
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h4
import com.thariqzs.wanderai.ui.theme.sh1
import com.thariqzs.wanderai.utils.TokenViewModel

@Composable
fun ProfileScreen(navController: NavController, tvm: TokenViewModel) {
    ProfileBody(navController, tvm)
}

@Composable
fun ProfileBody(navController: NavController, tvm: TokenViewModel) {
    Column(
        Modifier.fillMaxSize()
    ) {
        ProfileScreenHeader(navController)
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Gray200, RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    Modifier
                        .size(76.dp)
                        .background(Gray500, RoundedCornerShape(50.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_user_circle),
                        contentDescription = "ic_user_circle",
                        tint = Gray600,
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            )
                            .size(52.dp)
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                ) {
                    Text("Rey", style = sh1)
                    Text("reyganteng@gmail.com", style = b1)
                }
            }
            ActionCard(icon = R.drawable.ic_settings, label = "Account Settings")
            ActionCard(icon = R.drawable.ic_lock, label = "Privacy Policy")
            ActionCard(icon = R.drawable.ic_sticky_note, label = "Terms and Condition")
            ActionCard(icon = R.drawable.ic_help, label = "Help Center")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        ActionCard(
            icon = R.drawable.ic_logout, label = "Logout", navigateTo = {
                tvm.deleteToken()
                navController.navigate(Routes.Auth)
            }, modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .background(RedNormal)
        )
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
                .clickable { navController.navigate(Routes.Home) }
        )
        Text(text = "Profile", style = h4)
    }
}

@Composable
fun ActionCard(
    icon: Int, label: String, navigateTo: (() -> Unit)? = null,
    modifier: Modifier? = null
) {
    val blueLightWithOpacity = BlueLight.copy(alpha = 0.2f)

    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                if (navigateTo != null) {
                    navigateTo()
                }
            }
            .border(1.dp, blueLightWithOpacity, RoundedCornerShape(8.dp))
            .then(modifier ?: Modifier)
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
                tint = if (icon == R.drawable.ic_logout) {
                    Color.White
                } else {
                    OrangeNormal
                },
                modifier = Modifier
                    .size(28.dp)
                    .padding(start = 8.dp)
            )
            Text(
                label, style = b2, color = if (icon == R.drawable.ic_logout) {
                    Color.White
                } else {
                    Color.Black
                }
            )
        }
        if (icon != R.drawable.ic_logout) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                tint = BlueNormal,
                contentDescription = "ic_chevron_right"
            )
        }
    }
}
//
//@Preview(showBackground = true, widthDp = 360)
//@Composable
//fun Preview() {
//    ProfileScreen(rememberNavController())
//}