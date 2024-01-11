package com.example.willowhealth.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.willowhealth.HealthMetric
import com.example.willowhealth.presentation.main.MainViewModel
import com.example.willowhealth.presentation.ui.components.NavigationItems
import com.example.willowhealth.presentation.ui.screens.ChatScreen
import com.example.willowhealth.presentation.ui.screens.InsightsScreen
import com.example.willowhealth.presentation.ui.screens.SettingsScreen

@Composable
fun Navigation(viewModel: MainViewModel, ) {
    val navController = rememberNavController()
    val stepsData = viewModel.steps.observeAsState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val screens = listOf(
                    "insights_screen" to NavigationItems.Insights,
                    "chat_screen" to NavigationItems.Chat,
                    "settings_screen" to NavigationItems.Settings,
                )
                screens.forEach { (route, item) ->
                    BottomNavigationItem(
                        selected = currentRoute == route,
                        onClick = {
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(
                8.dp,
                paddingValues.calculateBottomPadding(),
                8.dp,
                8.dp
            )
        ) {
            NavHost(navController = navController, startDestination = "insights_screen") {
                composable("insights_screen") {
                    viewModel.fetchData(HealthMetric.STEPS)
                    InsightsScreen(stepsData.value)
                }
                composable("chat_screen") {
                    ChatScreen()
                }
                composable("settings_screen") {
                    SettingsScreen()
                }
                Log.d("MyApp", "NavHost!")
            }
        }
    }


}








