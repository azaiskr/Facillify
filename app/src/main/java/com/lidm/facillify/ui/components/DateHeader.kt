package com.lidm.facillify.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.ui.theme.Blue

@Composable
fun DateHeader(date: String) {
    Text(
        text = date,
        color = Blue,
        modifier = Modifier.padding(vertical = 8.dp),
        fontSize = 12.sp
    )
}