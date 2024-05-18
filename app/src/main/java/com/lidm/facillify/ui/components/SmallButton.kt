package com.lidm.facillify.ui.components

import android.widget.Button
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lidm.facillify.ui.theme.Blue

@Composable
fun SmallButton(
    onClick: () -> Unit,
    labelText: String
) {
    Button (
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = 32.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Text (
            text = labelText,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}