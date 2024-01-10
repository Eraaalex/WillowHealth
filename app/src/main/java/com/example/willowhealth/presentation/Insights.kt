package com.example.willowhealth.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.willowhealth.HealthMetric
import com.example.willowhealth.presentation.main.MainViewModel
import com.example.willowhealth.ui.theme.Green800
import com.example.willowhealth.ui.theme.components.NavigationItems

//@Preview
//@Composable
//fun InsightsPreview(){
//    Insights()
//}
//
//
//@Composable
//fun Insights(){
//    Steps(1000)
//
//}



/** Navigation bar **/

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Log.d("MyApp", "Start in ")
    val stepsData = viewModel.steps.observeAsState()

//    Text("kol")
    Log.d("MyApp", "Start in ")
    Scaffold(

        bottomBar = {
            BottomNavigation {
                Log.d("MyApp", "Start in BottomNavig 1")
                val openScreenIndex = remember {
                    mutableStateOf(0)
                }

                val navigationList = listOf(
                    NavigationItems.Insights,
                    NavigationItems.Chat,
                    NavigationItems.Settings,
                    NavigationItems.Name,
                )

                navigationList.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        selected = (index == openScreenIndex.value),
                        onClick = { openScreenIndex.value = index },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.secondary

                    )
                    Log.d("MyApp", "Start in BottomNavig 2")

                }

            }
        }
    ) {paddingValues ->
        // Use paddingValues to correctly position your content
        Box(modifier = Modifier.padding(paddingValues)) {

           stepsData.value?.let {
            Text(text = it.toString())
        }
        }
    }

}

@Composable
private fun FollowButton(
    isFollowed: Boolean,
    clickListener: () -> Unit
) {
    Button(
        onClick = { clickListener() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isFollowed) {
                MaterialTheme.colors.primary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colors.primary
            }
        )
    ) {
        val text = if (isFollowed) {
            "Unfollow"
        } else {
            "Follow"
        }
        Text(text = text)
    }
}
