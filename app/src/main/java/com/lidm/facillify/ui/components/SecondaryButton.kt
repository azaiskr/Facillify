package com.lidm.facillify.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.ui.theme.DarkBlue


@Composable
fun SecondaryButton(
    modifier: Modifier,
    onClick: () -> Unit,
    outline: Boolean,
    label: String,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (outline) Color.Transparent else DarkBlue,
            contentColor = if (outline) DarkBlue else Color.White,
        ),
        shape = RoundedCornerShape(16.dp),
        border = if (outline) BorderStroke(2.dp, DarkBlue) else null
    ) {
        Text(
            text = label,
            color = if (outline) DarkBlue else Color.White,
            fontSize = 16.sp,
        )
    }
}