package com.lidm.facillify.ui.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue
import java.util.Calendar
import java.util.regex.Pattern

@Composable
fun InputTextFieldDefault(
    topText: String,
    insideText: String,
    valueText: String,
    onValueChange: (String) -> Unit,
    isNumberOnly: Boolean = false,
    isPassword: Boolean = false,
    isDate: Boolean = false,
    isEmail: Boolean = false
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var emailError by remember { mutableStateOf(false) }
    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = topText, color = DarkBlue, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (isDate) {
                        val datePickerDialog = DatePickerDialog(
                            context,
                            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                val selectedDate = "${dayOfMonth.toString().padStart(2, '0')}/" +
                                        "${(month + 1).toString().padStart(2, '0')}/$year"
                                onValueChange(selectedDate)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                        datePickerDialog.show()
                    }
                },
            value = valueText,
            onValueChange = {newText ->
                if (!isDate){
                    if (isNumberOnly) {
                        onValueChange(newText)
                        newText.all { it.isDigit() }
                    } else if (isEmail) {
                        onValueChange(newText)
                        emailError = !emailPattern.matcher(newText).matches()
                    } else onValueChange(newText)
                }
            },
            placeholder = {
                Text(
                    text = insideText,
                    color = SecondaryBlue,
                    fontSize = 14.sp,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isEmail && emailError) Color.Red else Blue,
                unfocusedBorderColor = if (isEmail && emailError) Color.Red else SecondaryBlue,
                cursorColor = SecondaryBlue,
                focusedTextColor = Blue,
                unfocusedTextColor = if (valueText.isNotEmpty()) Blue else SecondaryBlue
            ),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = when {
                isNumberOnly -> KeyboardOptions(keyboardType = KeyboardType.Number)
                isPassword -> KeyboardOptions(keyboardType = KeyboardType.Password)
                isEmail -> KeyboardOptions(keyboardType = KeyboardType.Email)
                else -> KeyboardOptions(keyboardType = KeyboardType.Text)
            },
            visualTransformation = if (isPassword && !passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) {
                    val image = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisibility) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = image,
                            contentDescription = description,
                            tint = if (passwordVisibility) Blue else SecondaryBlue)
                    }
                }
            },
            enabled = !isDate,
            readOnly = isDate,
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

@Composable
@Preview(showBackground = true)
fun InputTextFieldDefaultPasswordPreview() {
    var pass by remember { mutableStateOf("") }

    InputTextFieldDefault(
        topText = "Password",
        insideText = "Enter your password",
        valueText = pass,
        onValueChange = { pass = it },
        isPassword = true
    )
}

@Composable
@Preview(showBackground = true)
fun InputTextFieldDefaultDatePreview() {
    var date by remember { mutableStateOf("") }

    InputTextFieldDefault(
        topText = "Date",
        insideText = "Enter your number",
        valueText = date,
        onValueChange = { date = it },
        isDate = true
    )
}