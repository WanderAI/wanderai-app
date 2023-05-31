package com.thariqzs.wanderai.ui.screens.auth

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.BlueOld
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h2
import com.thariqzs.wanderai.ui.theme.h4

@Composable
fun AuthScreen(navController: NavController) {
    AuthScreenBody(navController = navController)
}

@Composable
fun AuthScreenBody(navController: NavController) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column() {
        Image(
            painter = painterResource(id = R.drawable.auth_screen_banner),
            contentDescription = "auth_screen_banner",
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 120.dp)
                    .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Login",
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
                            .padding(bottom = 8.dp),
                        label = "E-Mail",
                        value = email,
                        onValueChange = { newVal -> email = newVal }
                    )
                    CustomTextInput(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = "Password",
                        value = password,
                        onValueChange = { newVal -> password = newVal }
                    )
                }
            }
            BottomActionButton(
                Modifier
                    .height(80.dp)
                    .align(Alignment.BottomCenter),
                onClick = { navController.navigate(Routes.Home) }
            )
        }
    }
}

@Composable
fun BottomActionButton(containerModifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = containerModifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Belum punya akun?", style = b2, modifier = Modifier)
            Text(
                "Sign Up",
                style = b2,
                color = BlueNormal,
                modifier = Modifier.padding(start = 4.dp)
            )
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
            Text("Masuk", style = h4)
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
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Column(modifier = containerModifier) {
        Text(label, style = h4, modifier = Modifier.padding(bottom = 4.dp))
        BasicTextField(value = value, onValueChange = onValueChange, modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
            textStyle = b2,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    AuthScreen(rememberNavController())
}
