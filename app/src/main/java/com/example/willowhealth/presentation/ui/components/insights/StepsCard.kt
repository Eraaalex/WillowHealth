package com.example.willowhealth.presentation.ui.components.insights

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.willowhealth.R
import com.example.willowhealth.presentation.ui.components.BasicCard
import com.example.willowhealth.presentation.ui.theme.Green500
import com.example.willowhealth.presentation.ui.theme.Green800
import com.example.willowhealth.presentation.ui.theme.Green900
import com.example.willowhealth.presentation.ui.theme.Red500

/**  Steps Card **/

@Composable
fun StepsCard(value: Int = 0, normalValue: Int = 6000) {
    BasicCard(
        title = "Steps",
        value = value,
        normalValue = normalValue,
        progressColor = Green800,
        backgroundColor = Green900,
        textColor = Green500
    )
}

@Composable
fun CaloriesCard(value: Int = 0, normalValue: Int = 400) {
    BasicCard(
        title = stringResource(R.string.calories),
        value = value,
        normalValue = normalValue,
        progressColor = Red500,
        backgroundColor = MaterialTheme.colors.surface,
        textColor = MaterialTheme.colors.onSurface,
    )
}


@Composable
fun CheckboxOption(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.clip(RoundedCornerShape(50))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview
@Composable
fun TriStateCheckboxSample() {
    Column {
        // define dependent checkboxes states
        val (state, onStateChange) = remember { mutableStateOf(true) }
        val (state2, onStateChange2) = remember { mutableStateOf(true) }


        val parentState = remember(state, state2) {
            if (state && state2) ToggleableState.On
            else if (!state && !state2) ToggleableState.Off
            else ToggleableState.Indeterminate
        }

        val onParentClick = {
            val s = parentState != ToggleableState.On
            onStateChange(s)
            onStateChange2(s)
        }

        TriStateCheckbox(
            state = parentState,
            onClick = onParentClick,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.primary
            )
        )
        Spacer(Modifier.size(25.dp))
        Column(Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
            Checkbox(state, onStateChange)
            Spacer(Modifier.size(25.dp))
            Checkbox(state2, onStateChange2)
        }
    }
}

