package com.example.willowhealth.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            SettingsButton(text = "Log out") {
                viewModel.logOut()
                AppRouter.navigateTo(Screen.LoginScreen)
            }
        }
    }
}

@Composable
fun SettingsButton(text: String, onClick: () -> Unit) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    onClick()
                }
            ) {   Text(text = text) }

        }
    }
}
