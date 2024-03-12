package com.example.willowhealth.presentation.ui.components.insights

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.willowhealth.presentation.ui.theme.LightBlue
import com.example.willowhealth.presentation.ui.theme.WillowTheme


/**  Sleep Card **/

@Composable
fun SleepCard(hours: Int = 0, mins: Int = 0, normalValue: Int = 8) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(0.dp, Color.Black),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Column(modifier = Modifier.padding(12.dp, 8.dp)) {
            Text(text = "Sleep", color = MaterialTheme.colors.onSurface, fontSize = 20.sp, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.onSurface,
                                    fontSize = 28.sp
                                )
                            ) {
                                append("$hours")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.onSecondary
                                )
                            ) {
                                append("hr ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.onSurface,
                                    fontSize = 28.sp
                                )
                            ) {
                                append("$mins")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.onSecondary
                                )
                            ) {
                                append("min")
                            }
                        }
                    )
                    Text(
                        text = "/$normalValue hours 00 min",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onSecondary,
                        fontSize = 15.sp
                    )
                }
                LinearProgressIndicator(
                    progress = (hours * 60 + mins) / (normalValue.toFloat() * 60),
                    modifier = Modifier
                        .height(15.dp)
                        .weight(1.5f)
                        .clip(RoundedCornerShape(8.dp)),
                    color = LightBlue
                )

            }
        }

    }
}


@Preview
@Composable
fun SleepPreview() {
    WillowTheme(darkTheme = true) {
        SleepCard(7, 0)
    }

}


