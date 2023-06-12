package com.thariqzs.wanderai.ui.screens.resetpassword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.screens.auth.AuthViewModel
import com.thariqzs.wanderai.ui.screens.auth.CustomTextInput
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.h4

@Composable
fun ResetPasswordScreen(navController: NavController, vm: AuthViewModel) {
    Column(Modifier.fillMaxSize()) {
        ResetPasswordScreenHeader(navController = navController)

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 36.dp)) {
            
            CustomTextInput(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                label = "E-Mail",
                value = vm.email,
                onValueChange = { newVal -> vm.email = newVal },
                placeholderText = "Masukkan E-mail",
                errMsg = vm.emailErr
            )
        }
    }
}

@Composable
fun ResetPasswordScreenHeader(navController: NavController) {
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
        Text(text = "Reset Password", style = h4)
    }
}

//@Preview(showBackground = true, widthDp = 360)
//@Composable
//fun Preview() {
//    ResetPasswordScreen(rememberNavController(), viewModel())
//}