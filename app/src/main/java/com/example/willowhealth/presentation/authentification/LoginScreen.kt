package com.example.willowhealth.presentation.authentification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.willowhealth.R
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.presentation.ui.components.ButtonComponent
import com.example.willowhealth.presentation.ui.components.OutlinedTextFieldLogIn
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(snackbarHostState: SnackbarHostState, viewModel: LoginViewModel = koinViewModel()) {
    val uiState by viewModel.uiState

    val scope = rememberCoroutineScope()
    val message by viewModel.snackbarMessage.collectAsState()
    var showDialog = remember { mutableStateOf(false) }
    val navigateToMain by viewModel.navigateToMainScreen.collectAsState()
    var email = remember { mutableStateOf("") }

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            scope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.clearSnackbarMessage()
            }
        }
    }
    LaunchedEffect(navigateToMain) {
        if (navigateToMain) {
            AppRouter.navigateTo(Screen.SplashScreen)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        OutlinedTextFieldLogIn(
            text = uiState.email,
            label = stringResource(R.string.email),
            onTextValueChanged = viewModel::onEmailChange
        )
        OutlinedTextFieldLogIn(
            text = uiState.phone,
            label = stringResource(R.string.phone),
            onTextValueChanged = viewModel::onPhoneChange
        )
        OutlinedTextFieldLogIn(
            text = uiState.password,
            label = stringResource(R.string.password),
            onTextValueChanged = viewModel::onPasswordChange,
            visual = PasswordVisualTransformation()
        )

        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = { showDialog.value = true }) {
            Text(stringResource(R.string.reset_password), color = MaterialTheme.colors.onPrimary)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ButtonComponent(
                text = stringResource(R.string.sign_in),
                onButtonClicked = {
                    viewModel.onSignInClick()
                },
                isEnabled = viewModel.allValidationPassed.value,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            ButtonComponent(
                text = stringResource(R.string.sign_up),
                onButtonClicked = {
                    viewModel.onSignUpClick()
                },
                isEnabled = viewModel.allValidationPassed.value,
                modifier = Modifier.weight(1f)
            )
        }
    }


    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = { Text("Reset Password") },
            text = {
                TextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Email") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        viewModel.resetPassword(email.value)
                    }
                ) {
                    Text("Reset")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

}




