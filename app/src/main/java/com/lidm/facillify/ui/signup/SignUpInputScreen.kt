package com.lidm.facillify.ui.signup

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.components.ButtonDefault
import com.lidm.facillify.ui.components.InputTextFieldDefault
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.util.Role

@Composable
fun SignUpInputScreen(
    selectedRole : Role,
    onSignUp: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        //State
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var birthPlace by remember { mutableStateOf("") }
        var nisn by remember { mutableStateOf("") }
        var nip by remember { mutableStateOf("") }
        var job by remember { mutableStateOf("") }
        var dateOfBirth by remember { mutableStateOf("") }

        val genderList = listOf("Laki-laki", "Perempuan")
        var selectedGender by rememberSaveable { mutableStateOf("") }

        val religionOption = listOf("Islam", "Kristen", "Katolik", "Hindu", "Budha", "Konghucu")
        var selectedReligion by rememberSaveable { mutableStateOf("") }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopSectionSignUp()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                when (selectedRole) {
                    Role.STUDENT -> {
                        Text(
                            text = "Mendaftar Sebagai Murid",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blue
                        )
                    }
                    Role.TEACHER -> {
                        Text(
                            text = "Mendaftar Sebagai Guru",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blue
                        )
                    }
                    else -> {
                        Text(
                            text = "Mendaftar Sebagai Orang Tua",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blue
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                //Text Field
                //Name
                InputTextFieldDefault(
                    topText = "Nama",
                    insideText = "Masukan nama",
                    valueText = name,
                    onValueChange = { name = it }
                )

                //Email
                Spacer(modifier = Modifier.height(16.dp))
                InputTextFieldDefault(
                    topText = "Email",
                    insideText = "Masukan email",
                    valueText = email ,
                    onValueChange = { email = it } ,
                    isEmail = true
                )

                //Password
                Spacer(modifier = Modifier.height(16.dp))
                InputTextFieldDefault(
                    topText = "Kata Sandi",
                    insideText = "Masukan kata sandi",
                    valueText = password,
                    onValueChange = { password = it },
                    isPassword = true
                )

                //Confirm Password
                Spacer(modifier = Modifier.height(16.dp))
                InputTextFieldDefault(
                    topText = "Konfirmasi Kata Sandi",
                    insideText = "Masukan ulang kata sandi",
                    valueText = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    isPassword = true
                )

                //Birth Place
                Spacer(modifier = Modifier.height(16.dp))
                InputTextFieldDefault(
                    topText = "Tempat Lahir",
                    insideText = "Masukan tempat lahir",
                    valueText = birthPlace,
                    onValueChange = { birthPlace = it }
                )

                //Birth Date
                Spacer(modifier = Modifier.height(16.dp))
                InputTextFieldDefault(
                    topText = "Tanggal Lahir",
                    insideText = "Masukan tanggal lahir",
                    valueText = dateOfBirth,
                    onValueChange = { dateOfBirth = it},
                    isDate = true
                )

                //Gender
                Spacer(modifier = Modifier.height(16.dp))
                SpinnerTemplate(
                    topText = "Jenis Kelamin",
                    listItem = genderList,
                    selectedItem = selectedGender,
                    onItemSelected = { selectedGender = it },
                    placeholder = "Pilih Jenis Kelamin"
                )

                //Address
                Spacer(modifier = Modifier.height(16.dp))
                InputTextFieldDefault(
                    topText = "Alamat",
                    insideText = "Masukan alamat",
                    valueText = address,
                    onValueChange = { address = it }
                )

                //Phone
                Spacer(modifier = Modifier.height(16.dp))
                InputTextFieldDefault(
                    topText = "Nomor Telepon",
                    insideText = "Masukan nomor telepon",
                    valueText = phone,
                    onValueChange = { phone = it },
                    isNumberOnly = true
                )

                //Religion
                Spacer(modifier = Modifier.height(16.dp))
                SpinnerTemplate(
                    topText = "Agama",
                    listItem = religionOption,
                    selectedItem = selectedReligion,
                    onItemSelected = { selectedReligion = it },
                    placeholder = "Pilih Agama"
                )

                Spacer(modifier = Modifier.height(16.dp))

                when (selectedRole) {
                    Role.STUDENT -> {
                        InputTextFieldDefault(
                            topText = "NISN",
                            insideText = "Masukan NISN",
                            valueText = nisn,
                            onValueChange = { nisn = it },
                            isNumberOnly = true
                        )
                    }
                    Role.TEACHER -> {
                        InputTextFieldDefault(
                            topText = "NIP",
                            insideText = "Masukan NIP",
                            valueText = nip,
                            onValueChange = { nip = it },
                            isNumberOnly = true
                        )
                    }
                    else -> {
                        InputTextFieldDefault(
                            topText = "Pekerjaan",
                            insideText = "Masukan pekerjaan",
                            valueText = job,
                            onValueChange = { job = it },
                        )
                    }
                }

                //Button
                Spacer(modifier = Modifier.height(32.dp))
                ButtonDefault(
                    text = "Daftar",
                    onClick = onSignUp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }
}

@Composable
fun TopSectionSignUp() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .fillMaxWidth(),
        //.offset { IntOffset(0, -100.dp.roundToPx()) },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier
            .size(400.dp)
            .offset { IntOffset(150, -325.dp.roundToPx()) }
        ) {
            drawCircle(color = Blue, radius = 400.dp.toPx())
        }
        Image(
            painter = painterResource(id = R.drawable.image_logo_white),
            contentDescription = "logo",
            modifier = Modifier
                .size(100.dp)
                .offset(100.dp, (-25).dp)
        )

    }
}

@Composable
fun SpinnerTemplate(
    topText: String,
    listItem: List<String>,
    selectedItem : String,
    onItemSelected: (selectedItem : String) -> Unit,
    placeholder: String
){
    var tempSelected by rememberSaveable { mutableStateOf(selectedItem) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val mContext = LocalContext.current
    Column {
        Text(text = topText, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold), color = Blue)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = { expanded = true },
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = if (selectedItem.isBlank()) SecondaryBlue else Blue,
            ),
            enabled = listItem.isNotEmpty(),
            border = BorderStroke(1.dp,
                if(expanded) {
                Blue
            } else SecondaryBlue),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (selectedItem.isBlank()) placeholder else tempSelected,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f),
                )
                Icon(if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = null)
            }
        }

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            listItem.forEach {item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        expanded = false
                        onItemSelected(item)
                        tempSelected = item
                        Toast.makeText(mContext, "Item terpilih : $item", Toast.LENGTH_SHORT).show()
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = Blue,
                        leadingIconColor = SecondaryBlue
                    ))
            }
        }
        //Debug
        //Text(text = "Selected Item : $selectedItem")
    }
}




@Composable
@Preview(showBackground = true)
fun SignUpInputScreenPreview() {
    SignUpInputScreen(
        selectedRole = Role.STUDENT
    )
}

@Composable
@Preview(showBackground = true)
fun TopSectionSignUpPreview() {
    TopSectionSignUp()
}

@Composable
@Preview(showBackground = true)
fun SpinnerTemplatePreview() {
    val genderList = listOf("Laki-laki", "Perempuan", "Genderuwo","Siluman Gondrong")
    var selectedGender by rememberSaveable { mutableStateOf("") }
    SpinnerTemplate(
        topText = "Jenis Kelamin",
        listItem = genderList,
        selectedItem = selectedGender,
        onItemSelected = { selectedGender = it },
        placeholder = "Pilih Jenis Kelamin"
    )
}