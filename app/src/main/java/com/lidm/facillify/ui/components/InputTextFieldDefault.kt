package com.lidm.facillify.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue

@Composable
fun InputTextFieldDefault(
    topText: String,
    insideText: String,
    valueText: String,
    onValueChange: (String) -> Unit
) {
    //State
    var inputText by remember { mutableStateOf("") }

    //UI
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = topText, color = DarkBlue, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = valueText,
            onValueChange = { onValueChange(it) },
            label = {
                Text(
                    text = insideText,
                    color = SecondaryBlue,
                ) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Blue,
                unfocusedBorderColor = SecondaryBlue,
                cursorColor = SecondaryBlue,
                focusedTextColor = Blue,
                unfocusedTextColor = SecondaryBlue
            ),
            shape = RoundedCornerShape(16.dp)
        )

    }
}

@Composable
@Preview(showBackground = true)
fun InputTextFieldDefaultPreview() {
    var email by remember { mutableStateOf("") }

    InputTextFieldDefault(
        topText = "Email",
        insideText = "Enter your email",
        valueText = email,
        onValueChange = { email = it }
    )
}