package com.example.willowhealth.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.willowhealth.presentation.main.MainViewModel
import com.example.willowhealth.ui.theme.components.NavigationItems

/** Navigation bar **/

@Composable
fun Screen(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val stepsData = viewModel.steps.observeAsState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                Log.d("MyApp", "openScreenIndex.value: ")
                val openScreenIndex = remember {
                    mutableStateOf(0)
                }
                val navigationList = listOf(
                    NavigationItems.Insights,
                    NavigationItems.Chat,
                    NavigationItems.Settings,
                )

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val screens = listOf(
                    "insights_screen" to NavigationItems.Insights,
                    "chat_screen" to NavigationItems.Chat,
                    "settings_screen" to NavigationItems.Settings,
                    // Add other screen destinations
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
//                navigationList.forEachIndexed { index, item ->
//                    BottomNavigationItem(
//                        selected = (index == openScreenIndex.value),
//                        onClick = { openScreenIndex.value = index },
//                        icon = {
//                            Icon(item.icon, contentDescription = null)
//                        },
//                        selectedContentColor = MaterialTheme.colors.onPrimary,
//                        unselectedContentColor = MaterialTheme.colors.secondary
//
//                    )
//                }
                Log.d("MyApp", "openScreenIndex.value: ${openScreenIndex.value}")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "insights_screen") {
                composable("insights_screen") {
                    InsightsScreen(stepsData.value.toString())
                }
                composable("chat_screen") {
                    ChatScreen()
                }
                composable("settings_screen") {
                    SettingsScreen()
                }
            }
        }
    }


}


@Composable
fun InsightsScreen(stepsData: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text(text = "Insights Screen")
            Text(text = stepsData)
        }
    }
}


@Composable
fun ChatScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Chat Screen")
    }
}

@Composable
fun SettingsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Settings Screen")
    }
}


// Define other screens similarly

