package com.example.willowhealth.presentation.ui.components.insights

import android.graphics.Paint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.willowhealth.R
import com.example.willowhealth.presentation.ui.theme.Grey500
import com.example.willowhealth.presentation.ui.theme.Grey600
import com.example.willowhealth.presentation.ui.theme.Grey800
import com.example.willowhealth.presentation.ui.theme.LightBlue
import com.example.willowhealth.utils.toShortString
import java.time.DayOfWeek

/**  Graphics Card **/

@Composable
internal fun BarChart(
    modifier: Modifier = Modifier.padding(4.dp),
    values: Map<DayOfWeek, Int>,
    maxHeight: Dp = 180.dp
) {
    val average = values.values.sumOf { it } / values.size
    val maxValue = values.values.maxOf { it }
    val averageLineY = with(LocalDensity.current) {
        (average * maxHeight.value / maxValue).dp.toPx()
    }
    val borderColor = Grey500
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colors.surface),
    ) {

        Text(
            stringResource(R.string.sleep_duration_title),
            modifier = Modifier.padding(16.dp, 8.dp),
            style = MaterialTheme.typography.h6
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 20.dp, 5.dp, 0.dp)
                .height(260.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = borderColor,
                            start = Offset(30f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = strokeWidth
                        )
                        // draw Y-Axis
                        drawLine(
                            color = borderColor,
                            start = Offset(30f, 0f),
                            end = Offset(30f, size.height),
                            strokeWidth = strokeWidth
                        )

                        val steps = 5
                        val stepValue = maxValue / (steps - 1)
                        val stepHeight = size.height / (steps - 1)
                        for (i in 0 until steps) {
                            val yPosition = size.height - (stepHeight * i)
                            val value = stepValue * i / 3600
                            drawContext.canvas.nativeCanvas.drawText(
                                value.toString(),
                                -4f,
                                yPosition + density.run { 0.dp.toPx() }, Paint().apply {
                                    color = Grey800.toArgb()
                                    textSize = density.run { 12.dp.toPx() }
                                    textAlign = Paint.Align.LEFT
                                }
                            )
                        }

                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                values.toList().sortedBy { day -> day.first }.forEach { (date, value) ->
                    val dayLabel = date.toShortString()
                    Bar(
                        value = (value),
                        label = dayLabel,
                        color = LightBlue,
                        maxHeight = maxHeight,
                        minHeight = 1.dp,
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
                    color = Grey600,
                    start = Offset(30f, averageLineY),
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
    minHeight: Dp,
    maxValue: Int
) {
    val itemHeight = remember(value) { value * maxHeight.value / maxValue }
    val barWeight = 0.75f
    val horizontalPadding = 8.dp

    Column(
        modifier = Modifier
            .weight(barWeight)
            .padding(horizontal = horizontalPadding)
            .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
            .background(color),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height((itemHeight.dp + minHeight).coerceAtLeast(minHeight))
                .fillMaxWidth()
                .background(color)
        )
        Text(
            text = label,
            modifier = Modifier.padding(top = 4.dp),
            color = MaterialTheme.colors.onPrimary,
            style = TextStyle(fontSize = 11.sp)
        )
    }
}


@Preview
@Composable
fun BarChartPreview() {
    BarChart(
        values = mutableMapOf(
            (DayOfWeek.MONDAY to 666),
            (DayOfWeek.TUESDAY to 686),
            (DayOfWeek.WEDNESDAY to 866),
            (DayOfWeek.THURSDAY to 966),
            (DayOfWeek.FRIDAY to 666),
            (DayOfWeek.SATURDAY to 600),
            (DayOfWeek.SUNDAY to 0),
        )
    )

}