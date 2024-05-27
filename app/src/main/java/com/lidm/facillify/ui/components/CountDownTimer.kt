package com.lidm.facillify.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AvTimer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.OnBlueSecondary
import com.lidm.facillify.ui.theme.YellowOrange
import kotlinx.coroutines.delay

@Preview
@Composable
fun CountDownTimer(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    totalTimeSeconds: Int = 60,
    onTimerFinished: () -> Unit = {}
) {
    val remainingTime = remember { mutableIntStateOf(totalTimeSeconds) }
    val animatedWidth = remember { Animatable(1f) }
    val color = remember { mutableStateOf(DarkBlue) }

    LaunchedEffect (Unit) {
        while(remainingTime.intValue > 0) {
            delay(1000L)
            remainingTime.intValue -= 1
            animatedWidth.animateTo((remainingTime.intValue.toFloat() / totalTimeSeconds.toFloat()))
            when {
                remainingTime.intValue <= totalTimeSeconds * 0.25 -> {
                    color.value = AlertRed
                }
                remainingTime.intValue <= totalTimeSeconds * 0.50 -> {
                    color.value = YellowOrange
                }
                else -> {
                    color.value = DarkBlue
                }
            }

        }
        onTimerFinished()
    }

    val minutes = (remainingTime.intValue / 60).toString().padStart(2, '0')
    val seconds = (remainingTime.intValue % 60).toString().padStart(2, '0')
    val timeDisplay = "$minutes:$seconds"

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(32.dp)
        ) {
            drawRoundRect(
                color = OnBlueSecondary,
                topLeft = Offset(0f, 0f),
                size = size,
                cornerRadius = CornerRadius(50f, 50f)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth().height(28.dp).padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(100))
        ){
            Canvas(
                modifier = modifier
                    .fillMaxWidth(animatedWidth.value)
                    .height(24.dp)
            ) {
                drawRoundRect(
                    color = color.value,
                    topLeft = Offset(0f, 0f),
                    size = size.copy(width = size.width, size.height),
                    cornerRadius = CornerRadius(100f, 100f)
                )
            }
        }
        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = timeDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color.White,
                modifier = modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 16.dp)
            )
            Icon(
                imageVector = Icons.Rounded.AvTimer,
                contentDescription = null,
                tint = Color.White,
                modifier = modifier
                    .size(36.dp)
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 8.dp)
            )
        }
    }
}