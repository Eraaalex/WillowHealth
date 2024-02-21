package com.example.willowhealth.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.data.datasource.FirebaseAuthDataSource

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Icon(
            Icons.Rounded.AccountCircle,
            contentDescription = "Account circle",
            modifier = Modifier.size(100.dp),
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = FirebaseAuthDataSource.getName(),
            style = MaterialTheme.typography.h6, color = MaterialTheme.colors.onSecondary
        )

        Spacer(modifier = Modifier.padding(20.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(5),
            backgroundColor = MaterialTheme.colors.surface
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = {
                onClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.primary
            ),
            modifier = Modifier.fillMaxWidth()

        ) { Text(text = text, color = MaterialTheme.colors.onSurface) }

    }

}

@Preview
@Composable
fun SettingsButton() {
    SettingsButton("Log Out", {})

}
