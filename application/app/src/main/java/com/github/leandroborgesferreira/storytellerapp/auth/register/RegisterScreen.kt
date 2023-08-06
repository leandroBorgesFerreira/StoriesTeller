package com.github.leandroborgesferreira.storytellerapp.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.leandroborgesferreira.storytellerapp.utils_module.ResultData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RegisterScreenBinding(registerViewModel: RegisterViewModel, onRegisterSuccess: () -> Unit) {
    LaunchedEffect(key1 = "registerViewModel", block = {
        registerViewModel.init()
    })

    RegisterScreen(
        nameState = registerViewModel.name,
        emailState = registerViewModel.email,
        passwordState = registerViewModel.password,
        registerState = registerViewModel.register,
        nameChanged = registerViewModel::nameChanged,
        emailChanged = registerViewModel::emailChanged,
        passwordChanged = registerViewModel::passwordChanged,
        onRegisterRequest = registerViewModel::onRegister,
        onRegisterSuccess = onRegisterSuccess,
    )
}

@Composable
fun RegisterScreen(
    nameState: StateFlow<String>,
    emailState: StateFlow<String>,
    passwordState: StateFlow<String>,
    registerState: StateFlow<ResultData<Unit>>,
    nameChanged: (String) -> Unit,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit,
    onRegisterRequest: () -> Unit,
    onRegisterSuccess: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val name by nameState.collectAsStateWithLifecycle()
        val email by emailState.collectAsStateWithLifecycle()
        val password by passwordState.collectAsStateWithLifecycle()
        val register by registerState.collectAsStateWithLifecycle()
        var showPassword by remember { mutableStateOf(false) }

        when (register) {
            is ResultData.Complete -> {
                LaunchedEffect(key1 = "navigation") {
                    onRegisterSuccess()
                }
            }

            is ResultData.Idle, is ResultData.Error -> {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 50.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name,
                        onValueChange = nameChanged,
                        singleLine = true,
                        placeholder = {
                            Text(text = "Name")
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        onValueChange = emailChanged,
                        singleLine = true,
                        placeholder = {
                            Text(text = "Email")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password,
                        onValueChange = passwordChanged,
                        singleLine = true,
                        placeholder = {
                            Text(text = "Password")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (showPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            if (showPassword) {
                                Icon(
                                    modifier = Modifier.clickable {
                                        showPassword = !showPassword
                                    },
                                    imageVector = Icons.Default.VisibilityOff,
                                    contentDescription = ""
                                )
                            } else {
                                Icon(
                                    modifier = Modifier.clickable {
                                        showPassword = !showPassword
                                    },
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = ""
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    TextButton(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .fillMaxWidth(),
                        onClick = onRegisterRequest
                    ) {
                        Text(text = "Register", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }

            is ResultData.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    RegisterScreen(
        nameState = MutableStateFlow(""),
        emailState = MutableStateFlow(""),
        passwordState = MutableStateFlow(""),
        registerState = MutableStateFlow(ResultData.Idle()),
        nameChanged = {},
        emailChanged = {},
        passwordChanged = {},
        onRegisterRequest = {},
        onRegisterSuccess = {},
    )
}
