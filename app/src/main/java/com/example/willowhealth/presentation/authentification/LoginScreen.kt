package com.example.willowhealth.presentation.authentification

import android.util.Log
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.presentation.ui.components.ButtonComponent
import com.example.willowhealth.presentation.ui.components.OutlinedTextFieldLogIn

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()) {
    val uiState by viewModel.uiState
    val showErrorSnackbar = remember { viewModel.showSnackbarState }


    val snackbarHostState = remember { SnackbarHostState() }
    val showSnackbar = remember { mutableStateOf(false) }

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
            label = "Email",
            onTextValueChanged = viewModel::onEmailChange
        )
        OutlinedTextFieldLogIn(
            text = uiState.phone,
            label = "Phone",
            onTextValueChanged = viewModel::onPhoneChange
        )
        OutlinedTextFieldLogIn(
            text = uiState.password,
            label = "Password",
            onTextValueChanged = viewModel::onPasswordChange,
            visual = PasswordVisualTransformation()
        )


        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ButtonComponent(
                text = "Sign In",
                onButtonClicked = {
                    viewModel.onSignInClick()
                    Log.d("MyApp", "Click on Sign In")
                    AppRouter.navigateTo(Screen.MainScreen)
                },
                isEnabled = viewModel.allValidationPassed.value,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            ButtonComponent(
                text = "Sign Up",

                onButtonClicked = {
                    viewModel.onSignUpClick()
                    Log.d("MyApp", "Click on Sign Up")
                    AppRouter.navigateTo(Screen.MainScreen)
                },
                isEnabled = viewModel.allValidationPassed.value,
                modifier = Modifier.weight(1f)
            )
        }

        if (showErrorSnackbar.value == true) {
            Snackbar(
                modifier = Modifier.padding(8.dp),

                ) {
                Text(text = "Incorrect")
            }
        }

        LaunchedEffect(showSnackbar.value) {
            if (showSnackbar.value) {
                snackbarHostState.showSnackbar("Please check your inputs")
                showSnackbar.value = false
            }
        }
    }


}


@Preview
@Composable
fun ButtonsPreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ButtonComponent(
            text = "Sign In",
            onButtonClicked = {
                {}
                Log.d("MyApp", "Click on Sign In")
                AppRouter.navigateTo(Screen.MainScreen)
            },
            isEnabled = true,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        ButtonComponent(
            text = "Sign Up",

            onButtonClicked = {
                {}
                Log.d("MyApp", "Click on Sign Up")
                AppRouter.navigateTo(Screen.MainScreen)
            },
            isEnabled = true,
            modifier = Modifier.weight(1f)
        )

    }
}



