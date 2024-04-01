package com.example.willowhealth.presentation.splash

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.willowhealth.R
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.main.TAG
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(viewModel: SplashViewModel = koinViewModel()) {
    Log.d(TAG, "SplashScreen")
    LaunchedEffect(key1 = true) {

        viewModel.loadData()
        AppRouter.navigateTo(Screen.MainScreen)
//        navController.navigate("loginScreen") {
//            popUpTo("splashScreen") { inclusive = true } // Удалить SplashScreen из стека
//        }
    }
    Log.d(TAG, "SplashScreen")
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(
            painter = painterResource(
                id = R.drawable.health_heart_icon
            ),
            contentDescription = "logo with health heart"
        )
    }
}

