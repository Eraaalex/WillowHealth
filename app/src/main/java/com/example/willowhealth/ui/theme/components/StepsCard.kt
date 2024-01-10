package com.example.willowhealth.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.willowhealth.ui.theme.Green500
import com.example.willowhealth.ui.theme.Green800
import com.example.willowhealth.ui.theme.Green900

/**  Steps Card **/

@Composable
fun Steps(value: Int = 0, normalValue: Int = 6000) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(0.dp, Color.Black),
        elevation = 2.dp,
        backgroundColor = Green900
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(12.dp, 8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = value.toString(), color = Green500, fontSize = 30.sp)
                Text(text = "/$normalValue", color = Green500, fontSize = 15.sp)
            }
            LinearProgressIndicator(
                progress = value / normalValue.toFloat(),
                modifier = Modifier
                    .height(15.dp)
                    .weight(1.5f)
                    .clip(RoundedCornerShape(8.dp)),
                color = Green800
            )

        }
    }
}


@Preview
@Composable
fun StepsPreview() {
    Steps(1000)
}


