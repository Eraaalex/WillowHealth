package com.example.willowhealth.presentation.splash

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.willowhealth.R
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.main.TAG
import com.example.willowhealth.presentation.ui.theme.Grey1100
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(viewModel: SplashViewModel = koinViewModel()) {
    val isHeartBeating = remember { mutableStateOf(false) }
    val heartScale by animateFloatAsState(targetValue = if (isHeartBeating.value) 1.2f else 1f)

    LaunchedEffect(key1 = true) {
        viewModel.loadData()
        AppRouter.navigateTo(Screen.MainScreen)
    }
    LaunchedEffect(key1 = true) {
        while (true) {
            isHeartBeating.value = !isHeartBeating.value
            delay(500)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.secondaryVariant), contentAlignment = Alignment.Center) {
        Icon(
            modifier = Modifier.scale(heartScale).size(150.dp),
            painter = painterResource(
                id = R.drawable.health_heart_icon
            ),
            contentDescription = "logo with health heart",
            tint = Color.Unspecified
        )
    }
}

