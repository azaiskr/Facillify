package com.lidm.facillify.ui.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.login.TopSection
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.fontFamily
import com.lidm.facillify.util.Role

@Composable
fun SignUpScreen(
    onSignUp: (role: Role) -> Unit = {},
    onSignUpAsStudent: (role:Role) -> Unit = {},
    onSignUpAsTeacher: (role:Role) -> Unit = {},
    onSignUpAsParent: (role:Role) -> Unit = {},
    onSignIn: () -> Unit = {}
) {
    //State

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopSection()
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Bergabung bersama FACILIFFY.",
                    color = Blue,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    fontFamily = fontFamily
                )
                Text(
                    text = "Mendaftar sebagai",
                    color = Black,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                ButtonAs(
                    onClick = { onSignUp(Role.STUDENT) },
                    imageIcon = ImageVector.vectorResource(id = R.drawable.ic_student ),
                    text = "Siswa"
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonAs(
                    onClick = { onSignUp(Role.TEACHER) },
                    imageIcon = ImageVector.vectorResource(id = R.drawable.ic_teacher),
                    text = "Guru"
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonAs(
                    onClick = { onSignUp(Role.PARENT) },
                    imageIcon = ImageVector.vectorResource(id = R.drawable.ic_people),
                    text = "Orang Tua"
                )
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    AlreadyHaveAcc(
                        onClick = onSignIn
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonAs(
    onClick: () -> Unit,
    imageIcon: ImageVector,
    text: String
){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = imageIcon, contentDescription = "icon" )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun AlreadyHaveAcc(
    onClick: () -> Unit
){
    Row {
        Text(
            text = "Sudah mempunyai akun?",
            color = Black,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier.clickable { onClick() },
            text = "Masuk",
            color = Blue,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )
    }
}


@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview() {
    SignUpScreen()
}

@Composable
@Preview(showBackground = true)
fun ButtonAsPreview() {
    ButtonAs(
        onClick = {},
        imageIcon = ImageVector.vectorResource(id = R.drawable.ic_student),
        text = "Siswa"
    )
}