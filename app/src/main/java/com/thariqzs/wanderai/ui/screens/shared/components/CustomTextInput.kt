package com.thariqzs.wanderai.ui.screens.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.Gray600
import com.thariqzs.wanderai.ui.theme.PlaceholderColor
import com.thariqzs.wanderai.ui.theme.RedNormal
import com.thariqzs.wanderai.ui.theme.a
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInput(
    containerModifier: Modifier?,
    label: String,
    value: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    placeholderText: String? = null,
    isPassword: Boolean? = false,
    errMsg: String? = "",
    rightLabel: String? = "",
    onClickRightLabel: (() -> Unit?)? = null,
    noLabel: Boolean? = false,
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    if (containerModifier != null) {
        Column(modifier = containerModifier) {
            if (noLabel == false) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(label, style = h4, modifier = Modifier.padding(bottom = 4.dp))
                    if (rightLabel != null) {
                        if (rightLabel.isNotBlank())
                            Text(
                                rightLabel,
                                style = b2,
                                color = RedNormal,
                                modifier = Modifier.clickable {
                                    if (onClickRightLabel != null) {
                                        onClickRightLabel()
                                    }
                                })
                    }
                }
            }
            BasicTextField(
                value = value, onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                textStyle = b2,
                singleLine = singleLine,
                visualTransformation = if (isPassword == true) {
                    if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                } else VisualTransformation.None,
                decorationBox = @Composable { innerTextField ->
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = value,
                        innerTextField = innerTextField,
                        enabled = enabled,
                        singleLine = singleLine,
                        interactionSource = interactionSource,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = RedNormal,
                        ),
                        visualTransformation = if (isPassword == true) {
                            if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                        } else VisualTransformation.None,
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        placeholder = {
                            Text(
                                text = placeholderText ?: "",
                                color = PlaceholderColor
                            )
                        },
                        isError = !errMsg.isNullOrBlank(),
                    )
                },
            )
            if (errMsg.isNullOrBlank()) {
                Spacer(modifier = Modifier.padding(bottom = 4.dp))
            } else {
                Text(
                    errMsg,
                    style = a,
                    color = RedNormal,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInputPassword(
    containerModifier: Modifier?,
    label: String,
    value: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    placeholderText: String? = null,
    isPassword: Boolean? = false,
    errMsg: String? = "",
    rightLabel: String? = "",
    onClickRightLabel: (() -> Unit?)? = null,
    noLabel: Boolean? = false,
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    if (containerModifier != null) {
        Column(modifier = containerModifier) {
            if (noLabel == false) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(label, style = h4, modifier = Modifier.padding(bottom = 4.dp))
                    if (rightLabel != null) {
                        if (rightLabel.isNotBlank())
                            Text(
                                rightLabel,
                                style = b2,
                                color = RedNormal,
                                modifier = Modifier.clickable {
                                    if (onClickRightLabel != null) {
                                        onClickRightLabel()
                                    }
                                })
                    }
                }
            }
            BasicTextField(
                value = value, onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                textStyle = b2,
                singleLine = singleLine,
                visualTransformation = if (isPassword == true) {
                    if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                } else VisualTransformation.None,
                decorationBox = @Composable { innerTextField ->
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = value,
                        innerTextField = innerTextField,
                        enabled = enabled,
                        singleLine = singleLine,
                        interactionSource = interactionSource,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = RedNormal,
                        ),
                        visualTransformation = if (isPassword == true) {
                            if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                        } else VisualTransformation.None,
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        placeholder = {
                            Text(
                                text = placeholderText ?: "",
                                color = PlaceholderColor
                            )
                        },
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
                        },
                        isError = !errMsg.isNullOrBlank(),
                    )
                },
            )
            if (errMsg.isNullOrBlank()) {
                Spacer(modifier = Modifier.padding(bottom = 4.dp))
            } else {
                Text(
                    errMsg,
                    style = a,
                    color = RedNormal,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInputChat(
    containerModifier: Modifier?,
    label: String,
    value: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    placeholderText: String? = null,
    isPassword: Boolean? = false,
    errMsg: String? = "",
    rightLabel: String? = "",
    onClickRightLabel: (() -> Unit?)? = null,
    noLabel: Boolean? = false,
    focusRequester: FocusRequester = FocusRequester(),
    onSendChat: () -> Unit
) {

    val backgroundColor = if (enabled) {
        BlueNormal
    } else {
        Gray600
    }

    if (containerModifier != null) {
        Column(modifier = containerModifier) {
            if (noLabel == false) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(label, style = h4, modifier = Modifier.padding(bottom = 4.dp))
                    if (rightLabel != null) {
                        if (rightLabel.isNotBlank())
                            Text(
                                rightLabel,
                                style = b2,
                                color = RedNormal,
                                modifier = Modifier.clickable {
                                    if (onClickRightLabel != null) {
                                        onClickRightLabel()
                                    }
                                })
                    }
                }
            }
            Row() {
                BasicTextField(
                    value = value, onValueChange = onValueChange,
                    modifier = Modifier
                        .height(40.dp)
                        .weight(1f)
                        .focusRequester(focusRequester),
                    textStyle = b2,
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = VisualTransformation.None,
                    decorationBox = @Composable { innerTextField ->
                        TextFieldDefaults.TextFieldDecorationBox(
                            value = value,
                            innerTextField = innerTextField,
                            enabled = enabled,
                            singleLine = singleLine,
                            interactionSource = interactionSource,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = RedNormal,
                            ),
                            visualTransformation = VisualTransformation.None,
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp),
                            placeholder = {
                                Text(
                                    text = placeholderText ?: "",
                                    color = PlaceholderColor
                                )
                            },
                            isError = !errMsg.isNullOrBlank(),
                        )
                    },
                )
                Box(modifier = Modifier
                    .padding(start = 16.dp)
                    .size(40.dp)
                    .background(backgroundColor, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onSendChat()
                        focusRequester.freeFocus()
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send_chat),
                        contentDescription = "send chat",
                        tint = Color.White,
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            }

            if (errMsg.isNullOrBlank()) {
                Spacer(modifier = Modifier.padding(bottom = 4.dp))
            } else {
                Text(
                    errMsg,
                    style = a,
                    color = RedNormal,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}