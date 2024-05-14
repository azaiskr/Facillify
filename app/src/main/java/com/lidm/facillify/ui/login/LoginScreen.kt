package com.lidm.facillify.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidm.facillify.R
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue

@Composable
fun LoginScreen() {
    Surface(color = Color.White){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TopSection()

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Selamat Datang di Facillify.",
                    color = Blue,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Input Boxes
            Text(
                text = "Email",
                color = DarkBlue,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 32.dp)
            )
            InputBox(text = "Masukkan email")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Kata Sandi",
                color = Blue,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 32.dp)
            )
            InputBox(text = "Masukkan kata sandi")
            Spacer(modifier = Modifier.height(16.dp))
            // Button
            }
        }
    }

@Composable
private fun TopSection() {
    Box(
        modifier = Modifier
            .size(400.dp)
            .background(Blue),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_logo_white),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
    }
}

@Composable
fun InputBox(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(48.dp)
            .border(
                width = 2.dp,
                color = Color.Blue.copy(alpha = 0.7f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = text)
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen()
}
