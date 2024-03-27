package com.example.willowhealth.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.willowhealth.app.CHAT_SCREEN
import com.example.willowhealth.app.INSIGHTS_SCREEN
import com.example.willowhealth.app.SETTINGS_SCREEN
import com.example.willowhealth.presentation.chat.ChatScreen
import com.example.willowhealth.presentation.insights.InsightsScreen
import com.example.willowhealth.presentation.settings.SettingsScreen
import com.example.willowhealth.presentation.ui.components.NavigationItems

@Composable
fun MainScreen() {
    val surveyNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by surveyNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val screens = listOf(
                    INSIGHTS_SCREEN to NavigationItems.Insights,
                    CHAT_SCREEN to NavigationItems.Chat,
                    SETTINGS_SCREEN to NavigationItems.Settings,
                )
                screens.forEach { (route, item) ->
                    BottomNavigationItem(
                        selected = currentRoute == route,
                        onClick = {
                            surveyNavController.navigate(route) {
                                popUpTo(surveyNavController.graph.startDestinationId) {
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
                8.dp,
                8.dp,
                paddingValues.calculateBottomPadding()
            )
        ) {
            NavHost(
                navController = surveyNavController,
                startDestination = INSIGHTS_SCREEN
            ) { // TODO INSIGHTS_SCREEN
                composable(INSIGHTS_SCREEN) {
                    InsightsScreen()
                }
                composable(CHAT_SCREEN) {
                    ChatScreen()
                }
                composable(SETTINGS_SCREEN) {
                    SettingsScreen()
                }

            }
        }
    }


}








