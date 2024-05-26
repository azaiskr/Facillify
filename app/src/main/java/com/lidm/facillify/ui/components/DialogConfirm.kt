package com.lidm.facillify.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue

@Composable
fun DialogConfirm(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(
            text = "Kumpulkan jawaban",
            color = DarkBlue,
            fontWeight = FontWeight.Bold
        ) },
        text = { Text(text = "Apakah kamu sudah yakin dengan semua jawabanmu? Pastikan semua soal telah terjawab ya") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    contentColor = Color.White
                )
            ) {
                Text("Ya")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Blue
                )
            ) {
                Text("Kembali", fontWeight = FontWeight.Bold)
            }
        }
    )
}
