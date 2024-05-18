package com.lidm.facillify.ui.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue

@Composable
fun LoginScreen(
    onLogin: () -> Unit = {},
    onSignUp: () -> Unit = {}
) {
    Surface(color = Color.White){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopSection()
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Selamat Datang di Facillify.",
                    color = Blue,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp

                )

                //Text Field
                Spacer(modifier = Modifier.height(16.dp))
                InputBox(
                    topText = "Email",
                    insideBox = "Masukkan email"
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputBox(
                    topText = "Password",
                    insideBox = "Masukkan password"
                )

                //Button
                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomButton(
                        text = "Masuk",
                        onClick = onLogin
                    )
                    SignUpText(
                        onClick = onSignUp
                    )
                }
            }
        }
    }
}

@Composable
private fun TopSection() {
    Box(
        modifier = Modifier
            .size(350.dp)
            .fillMaxWidth(),
            //.offset { IntOffset(0, -100.dp.roundToPx()) },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier
            .size(400.dp)
            .offset { IntOffset(-100, -250.dp.roundToPx()) }
        ) {
            drawCircle(color = Blue, radius = 400.dp.toPx())
        }
        Image(
            painter = painterResource(id = R.drawable.image_logo_white),
            contentDescription = "logo",
            modifier = Modifier.size(200.dp)
        )

    }
}

@Composable
fun InputBox(
    topText: String,
    insideBox: String,
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
            value = inputText,
            onValueChange = {
                inputText = it
            },
            label = {
                Text(
                    text = insideBox,
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
fun CustomButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SignUpText(
    onClick: () -> Unit
){
    Row {
        Text(
            text = "Belum punya akun?",
            color = Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = " Daftar",
            color = Blue,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable { onClick() }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen()
}

@Composable
@Preview(showBackground = true)
fun InputBoxPreview() {
    InputBox(
        topText = "Email",
        insideBox = "Masukkan email"
    )
}

@Composable
@Preview(showBackground = true)
fun CustomButtonPreview() {
    CustomButton(
        text = "Masuk",
        onClick = {}
    )
}
