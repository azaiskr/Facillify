package com.lidm.facillify.ui.login

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.R
import com.lidm.facillify.di.Inject
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.ui.viewmodel.AuthViewModel
import com.lidm.facillify.util.ResponseState

@Composable
fun LoginScreen(
    onLogin: () -> Unit = {},
    onSignUp: () -> Unit = {},
    context: Context = LocalContext.current,
) {
    //State
    val authViewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )

    val loginResultState = authViewModel.loginState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect (loginResultState){
        when(loginResultState.value) {
            is ResponseState.Loading -> {}
            is ResponseState.Success -> {
                onLogin()
            }
            is ResponseState.Error -> {
                Toast.makeText(
                    context,
                    (loginResultState.value as ResponseState.Error).error,
                    Toast.LENGTH_SHORT
                ).show()
                (loginResultState.value as ResponseState.Error).error.let { /*Log.d("UserLogin", it)*/ }
            }
        }
    }

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
                    text = "Selamat Datang di FACILIFFY.",
                    color = Blue,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                //Text Field
                Spacer(modifier = Modifier.height(16.dp))
                InputBox(
                    topText = "Email",
                    insideField = "Masukkan email",
                    valueText = email,
                    onValueChange = { email = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputBox(
                    topText = "Password",
                    insideField = "Masukkan password",
                    valueText = password,
                    onValueChange = { password = it }
                )

                //Button
                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomButton(
                        text = "Masuk",
                        onClick = {
                            authViewModel.login(email,password)
                        }
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
fun TopSection() {
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
            modifier = Modifier.size(150.dp)
        )

    }
}

@Composable
fun InputBox(
    topText: String,
    insideField: String,
    valueText: String,
    onValueChange: (String) -> Unit
) {
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
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(
                    text = insideField,
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
    var teks by remember { mutableStateOf("") }
    InputBox(
        topText = "Email",
        insideField = "Masukkan email",
        valueText = teks,
        onValueChange = { teks = it}
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
