package com.thariqzs.wanderai.ui.screens.auth

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.screens.shared.components.CustomTextInput
import com.thariqzs.wanderai.ui.screens.shared.components.CustomTextInputPassword
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.BlueOld
import com.thariqzs.wanderai.ui.theme.PlaceholderColor
import com.thariqzs.wanderai.ui.theme.RedNormal
import com.thariqzs.wanderai.ui.theme.a
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h2
import com.thariqzs.wanderai.ui.theme.h4
import com.thariqzs.wanderai.utils.CoroutinesErrorHandler
import com.thariqzs.wanderai.utils.TokenManager
import com.thariqzs.wanderai.utils.TokenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(navController: NavController, vm: AuthViewModel, tvm: TokenViewModel) {
    val context = LocalContext.current
    val TAG = "asthoriq"

    val loginRes by vm.loginResponse.observeAsState()

    when (val response = loginRes) {
        is ApiResponse.Success -> {
            val data = response.data
            if (tvm.token.value.isNullOrBlank()) {
                navController.navigate(Routes.Home)
                data.data?.let { tvm.saveToken(it) }
            }
        }

        is ApiResponse.Failure -> {
            val errorMessage = response.errorMessage
            Toast.makeText(context, "${errorMessage}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "failure: ${errorMessage}")
        }

        is ApiResponse.Loading -> {
            Log.d(TAG, "loading... ")
            Toast.makeText(context, "Please wait..", Toast.LENGTH_SHORT).show()
            // Handle loading state if needed
        }

        else -> {
//            Log.d(TAG, "else: ${response}")
        }
    }

    val store = TokenManager(context)
    val token = store.getToken().collectAsState(initial = null)

    val coroutineScope = rememberCoroutineScope()
    val clickCount = remember { mutableStateOf(0) }

    BackHandler(true) {
        if (clickCount.value == 0) {
            // First click, start a coroutine to wait for the second click
            clickCount.value++
            coroutineScope.launch {
                delay(2000) // Adjust the delay duration as needed (in milliseconds)
                clickCount.value = 0
            }
            Toast.makeText(context, "Klik 2 kali untuk keluar", Toast.LENGTH_SHORT).show()
        } else {
            // Second click, close the app
            val activity = context as? Activity
            activity?.finish()
        }
    }

    LaunchedEffect(Unit) {
        if (token.value != null) {
            Log.d(TAG, "token.value: ${token.value}")
            navController.navigate(Routes.Home)
        }
    }

    AuthScreenBody(navController, vm)
}

@Composable
fun AuthScreenBody(navController: NavController, viewModel: AuthViewModel) {
    val context = LocalContext.current

    if (viewModel.currTab == "Login") {
        viewModel.validateInputLogin()
    } else if (viewModel.currTab == "Sign Up") {
        viewModel.validateInputRegister()
    }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.auth_screen_banner),
            contentDescription = "auth_screen_banner",
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 48.dp)
                    .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    viewModel.currTab,
                    style = h2,
                    color = BlueOld,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    CustomTextInput(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        label = "E-Mail",
                        value = viewModel.email,
                        onValueChange = { newVal -> viewModel.email = newVal },
                        placeholderText = "Masukkan E-mail",
                        errMsg = viewModel.emailErr
                    )
                    if (viewModel.currTab == "Sign Up") {
                        CustomTextInput(
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            label = "Nama",
                            value = viewModel.name,
                            onValueChange = { newVal -> viewModel.name = newVal },
                            placeholderText = "Masukkan Nama",
                            errMsg = viewModel.nameErr
                        )
                    }
                    CustomTextInputPassword(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = "Password",
                        value = viewModel.password,
                        onValueChange = { newVal -> viewModel.password = newVal },
                        placeholderText = "Masukkan Password",
                        isPassword = true,
                        errMsg = viewModel.passErr,
                        rightLabel = (if (viewModel.currTab == "Login") {
                            ""
                        } else {
                            ""
                        }).toString(),
                        onClickRightLabel = { navController.navigate(Routes.ResetPassword) }
                    )
                    if (viewModel.currTab == "Sign Up") {
                        CustomTextInputPassword(
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            label = "Konfirmasi Password",
                            value = viewModel.passwordConf,
                            onValueChange = { newVal -> viewModel.passwordConf = newVal },
                            placeholderText = "Masukkan Password",
                            isPassword = true,
                            errMsg = viewModel.passConfErr
                        )
                    }
                }
                BottomActionButton(
                    Modifier.height(72.dp),
                    onClick = {
                        if (viewModel.currTab == "Login") {
//                            if (viewModel.emailErr.isBlank() && viewModel.passErr.isBlank() && viewModel.email.isNotBlank() && viewModel.password.isNotBlank()) {
                            if (viewModel.emailErr.isBlank() && viewModel.passErr.isBlank() && viewModel.email.isNotBlank() && viewModel.password.isNotBlank()) {
                                viewModel.login(object : CoroutinesErrorHandler {
                                    override fun onError(message: String) {
                                        Log.d("asthoriq login", "onError: $message")
                                    }
                                })
                            }
                            else {
                                Toast.makeText(context, "Gagal masuk!", Toast.LENGTH_SHORT).show()
                            }
                        } else if (viewModel.currTab == "Sign Up") {
                            if (viewModel.emailErr.isBlank() && viewModel.passErr.isBlank() && viewModel.passConfErr.isBlank() && viewModel.nameErr.isBlank() && viewModel.email.isNotBlank() && viewModel.password.isNotBlank() && viewModel.name.isNotBlank() && viewModel.passwordConf.isNotBlank()) {
                                viewModel.register(object : CoroutinesErrorHandler {
                                    override fun onError(message: String) {
                                        Log.d("asthoriq register", "onError: $message")
                                    }
                                })
                            } else {
                                Toast.makeText(context, "Gagal daftar!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    clickSwitchTab = {
                        val newCurrTab = when (viewModel.currTab) {
                            "Login" -> "Sign Up"
                            "Sign Up" -> "Login"
                            else -> "Login"
                        }
                        viewModel.currTab = newCurrTab
                        viewModel.password = ""
                        viewModel.passwordConf = ""
                        viewModel.name = ""


                    },
                    currTab = viewModel.currTab,
                )

            }
        }
    }

}

@Composable
fun BottomActionButton(
    containerModifier: Modifier, onClick: () -> Unit, clickSwitchTab: () -> Unit, currTab: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = containerModifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Text(when (currTab) {
                "Login" -> "Belum punya akun?"
                "Sign Up" -> "Sudah punya akun?"
                else -> "Belum punya akun?"}, style = b2, modifier = Modifier)
            Text(when (currTab) {
                "Login" -> "Sign Up"
                "Sign Up" -> "Login"
                else -> "Login"
            },
                style = b2,
                color = BlueNormal,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) { clickSwitchTab() })
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(40.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BlueNormal),
            onClick = onClick
        ) {
            Text(
                text = when (currTab) {
                    "Login" -> "Masuk"
                    "Sign Up" -> "Daftar"
                    else -> "Masuk"
                }, style = h4
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//    AuthScreen(rememberNavController())
//}
