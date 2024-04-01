package com.example.willowhealth.presentation.ui.components.insights

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.willowhealth.model.MissionData

@Composable
fun MissionCard(
    missions: List<MissionData>,
    onUpdateMissionChecked: (Int, Boolean) -> Unit
) {
    val selectedMissions = mutableStateOf(missions)
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
            Column(modifier = Modifier)
            {
                selectedMissions.value.forEach { mission ->
                    CheckboxOption(
                        checked = mission.isChecked,
                        onCheckedChange = { isChecked ->
                            val updatedList = selectedMissions.value.toMutableList().also {
                                val index = it.indexOfFirst { m -> m.number == mission.number }
                                if (index != -1) {
                                    it[index] = mission.copy(isChecked = isChecked)
                                }
                            }
                            selectedMissions.value = updatedList
                            onUpdateMissionChecked(mission.number ?: 0, isChecked)
                        },
                        label = mission.missionShort
                    )
                }
            }

        }

    }
}
