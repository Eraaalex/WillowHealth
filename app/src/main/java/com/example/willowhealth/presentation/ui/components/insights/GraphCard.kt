package com.example.willowhealth.presentation.ui.components.insights

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.willowhealth.presentation.ui.theme.Grey500
import com.example.willowhealth.presentation.ui.theme.Grey600
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**  Graphics Card **/

@Composable
internal fun BarChart(
    modifier: Modifier = Modifier.padding(8.dp),
    values: List<Pair<LocalDate, Int>>,
    maxHeight: Dp = 180.dp
) {
    val average = values.sumOf { it.second } / values.size
    val maxValue = values.maxOf { it.second }
    val averageLineY = with(LocalDensity.current) {
        (average * maxHeight.value / maxValue).dp.toPx()
    }
    val borderColor = Grey500
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp, 20.dp)
                .height(maxHeight)
        ) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = borderColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = strokeWidth
                        )
                        // draw Y-Axis
                        drawLine(
                            color = borderColor,
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = strokeWidth
                        )
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // Ensure there are 7 bars in the chart
                val fullValues = if (values.size < 7) {
                    List(7 - values.size) { LocalDate.now() to 0 } + values
                } else {
                    values
                }

                val formatter = DateTimeFormatter.ofPattern("EEE")

                fullValues.forEach { (date, value) ->
                    val dayLabel = date.format(formatter)
                    Bar(
                        value = value,
                        label = dayLabel,
                        color = MaterialTheme.colors.onBackground,
                        maxHeight = maxHeight,
                        maxValue = maxValue
                    )
                }


            }
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .padding(8.dp, 0.dp)
            ) {
                drawLine(
                    color = borderColor,
                    start = Offset(0f, averageLineY),
                    end = Offset(size.width, averageLineY),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            }
        }

    }
}

@Composable
private fun RowScope.Bar(
    value: Int,
    label: String,
    color: Color,
    maxHeight: Dp,
    maxValue: Int
) {
    val itemHeight = remember(value) { value * maxHeight.value / maxValue }

    Column(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
            .background(Grey600),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(itemHeight.dp)
                .fillMaxWidth()
                .background(Grey600)
        )
        Text(
            text = label,
            modifier = Modifier.padding(top = 4.dp),
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
fun BarChartPreview() {
    BarChart(
        values = listOf(
            LocalDate.now() to 100,
            LocalDate.now() to 200,
            LocalDate.now() to 279,
            LocalDate.now() to 300,
            LocalDate.of(2024, 1, 1) to 300,
            LocalDate.now() to 700,
            LocalDate.now() to 300,
        )
    )

}