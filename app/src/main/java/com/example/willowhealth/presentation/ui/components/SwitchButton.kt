package com.example.willowhealth.presentation.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.willowhealth.R
import com.example.willowhealth.presentation.settings.AppTheme
import com.example.willowhealth.presentation.ui.theme.Green900
import com.example.willowhealth.presentation.ui.theme.Grey700

@Composable
fun SwitchButton(
    selectedTheme: AppTheme,
    onItemSelected: (AppTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    val themeInit = isSystemInDarkTheme()
    val darkTheme = remember { mutableStateOf(themeInit) }
    val sourceId = if (darkTheme.value) R.drawable._moon_icon else R.drawable._sun_icon
    Row(modifier = modifier.padding(8.dp)) {
        Icon(
            imageVector = ImageVector.vectorResource(id = sourceId),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(24.dp)
                .weight(0.3f)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = "Night mode", modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface
        )
        Switch(
            checked = darkTheme.value,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.White,
                checkedTrackColor = Green900,
                uncheckedTrackColor = Grey700
            ),
            onCheckedChange = { isChecked ->
                if (isChecked) {
                    onItemSelected(AppTheme.MODE_NIGHT)
                } else {
                    onItemSelected(AppTheme.MODE_DAY)
                }
                darkTheme.value = isChecked
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
