package com.thariqzs.wanderai.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.BlueOld
import com.thariqzs.wanderai.ui.theme.PlaceholderColor
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h2
import com.thariqzs.wanderai.ui.theme.h4
import com.thariqzs.wanderai.utils.CoroutinesErrorHandler

@Composable
fun AuthScreen(navController: NavController, vm: AuthViewModel) {
//    val vm: AuthViewModel = viewModel()
    AuthScreenBody( navController, vm )
}

@Composable
fun AuthScreenBody(navController: NavController, viewModel: AuthViewModel) {
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
                    viewModel.currTab, style = h2, color = BlueOld, modifier = Modifier.padding(bottom = 6.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    CustomTextInput(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = "E-Mail",
                        value = viewModel.email,
                        onValueChange = { newVal -> viewModel.email = newVal },
                        placeholderText = "Masukkan E-mail"
                    )
                    if (viewModel.currTab == "Sign Up") {
                        CustomTextInput(
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            label = "Nama",
                            value = viewModel.name,
                            onValueChange = { newVal -> viewModel.name = newVal },
                            placeholderText = "Masukkan Nama"
                        )
                        CustomTextInput(
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            label = "Konfirmasi Password",
                            value = viewModel.passwordConf,
                            onValueChange = { newVal -> viewModel.passwordConf = newVal },
                            placeholderText = "Masukkan Password",
                            isPassword = true
                        )
                    }
                    CustomTextInput(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = "Password",
                        value = viewModel.password,
                        onValueChange = { newVal -> viewModel.password = newVal },
                        placeholderText = "Masukkan Password",
                        isPassword = true
                    )
                }
                BottomActionButton(
                    Modifier.height(72.dp),
                    onClick = {
                              viewModel.login( object: CoroutinesErrorHandler {
                                  override fun onError(message: String) {
                                      Log.d("asthoriq", "onError: $message")
//                                      loginTV.text = "Error! $message"
                                  }
                              })
//                        navController.navigate(Routes.Home)
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
            Text("Belum punya akun?", style = b2, modifier = Modifier)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInput(
    containerModifier: Modifier,
    label: String,
    value: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    placeholderText: String? = null,
    isPassword: Boolean? = false
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    Column(modifier = containerModifier) {
        Text(label, style = h4, modifier = Modifier.padding(bottom = 4.dp))
        BasicTextField(
            value = value, onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            textStyle = b2,
            visualTransformation = if (isPassword == true) {
                if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else VisualTransformation.None,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(value = value,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    singleLine = singleLine,
                    interactionSource = interactionSource,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    visualTransformation = if (isPassword == true) {
                        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                    } else VisualTransformation.None,
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    placeholder = { Text(text = placeholderText ?: "", color = PlaceholderColor) },
                    trailingIcon = {
                        if (isPassword == true) {
                            val interactionSource = remember { MutableInteractionSource() }
                            val image = if (passwordVisible) R.drawable.ic_visibility_on
                            else R.drawable.ic_visibility_off
                            val description =
                                if (passwordVisible) "Hide password" else "Show password"
                            Icon(painter = painterResource(id = image),
                                description,
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable(
                                        interactionSource = interactionSource, indication = null
                                    ) { passwordVisible = !passwordVisible })
                        }
                    })
            },
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//    AuthScreen(rememberNavController())
//}
