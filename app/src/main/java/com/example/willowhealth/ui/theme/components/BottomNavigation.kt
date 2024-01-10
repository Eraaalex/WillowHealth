package com.example.willowhealth.ui.theme.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.willowhealth.R


sealed class NavigationItems(val titleResId: Int, val icon: ImageVector) {

    object Insights : NavigationItems(
        titleResId = R.string.navigation_item_insights,
        icon = Icons.Outlined.Home
    )

    object Chat : NavigationItems(
        titleResId = R.string.navigation_item_chat,
        icon = Icons.Outlined.Send
    )

    object Settings : NavigationItems(
        titleResId = R.string.navigation_item_settings,
        icon = Icons.Outlined.Settings
    )

    object Name : NavigationItems(
        titleResId = R.string.navigation_item_name,
        icon = Icons.Outlined.Clear
    )
}
