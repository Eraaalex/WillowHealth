package com.example.willowhealth.presentation.ui.components.insights

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.willowhealth.model.MissionData

@Composable
fun MissionCard(
    value: List<MissionData>,
    onUpdateMissionChecked: (Int, Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(12.dp, 8.dp)
        ) {
            Title(text = "Missions")
            Column {
                value.forEach {
                    CheckboxOption(
                        checked = it.isChecked,
                        onCheckedChange = { isChecked ->
                            onUpdateMissionChecked(it.number ?: 0, isChecked)
                        },
                        label = it.missionShort
                    )
                }
            }

        }

    }
}
