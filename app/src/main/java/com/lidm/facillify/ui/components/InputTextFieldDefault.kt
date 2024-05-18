package com.lidm.facillify.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
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
    onValueChange: (String) -> Unit,
    isNumberOnly: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = topText, color = DarkBlue, fontSize = 16.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = valueText,
            onValueChange = {newText ->
                if (isNumberOnly) {
                    onValueChange(newText)
                    newText.all { it.isDigit() }
                } else onValueChange(newText)
                            },
            placeholder = {
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
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = if (isNumberOnly) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1

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