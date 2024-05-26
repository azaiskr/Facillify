package com.lidm.facillify.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.components.SecondaryButton
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.OnBlueSecondary
import com.lidm.facillify.util.Role

data class Siswa(
    val imgProfile: Int,
    val name: String,
    val email: String,
    val birthPlace: String,
    val birthDate: String,
    val gender: String,
    val address: String,
    val phone: String,
    val religion: String,
    val nisn: String,
    val parent: String? = null,
    val role: Role = Role.STUDENT,
)

@Composable
fun ProfileScreen(
    modifier: Modifier,
    navigateToFormTambahDataOrtu: () -> Unit ={},
    role: Role = Role.STUDENT,
) {
    //TODO: dummy data, replace with real data
    val siswa = Siswa(
        imgProfile = R.drawable.ic_launcher_background,
        name = "Siswa 1",
        email = "a@a.com",
        birthPlace = "Jakarta",
        birthDate = "01/01/2000",
        gender = "Laki-laki",
        address = "Jl. Jalan Jalan",
        phone = "08123456789",
        religion = "Islam",
        nisn = "1234567890",
        role = Role.STUDENT
    )
    //        when (val response = viewModel.materiBelajar){
//            is Response.Loading -> {
//                /*TODO*/
//            }
//            is Response.Success -> {
//                /*TODO*/
//            }
//            is Response.Error -> {
//                /*TODO*/
//            }
//        }
    ProfileContent(
        profileData = siswa,
        modifier = modifier,
        onClick = navigateToFormTambahDataOrtu,
        role = role,
    )
}

@Composable
fun ProfileContent(
    profileData: Siswa,
    modifier: Modifier,
    onClick: () -> Unit = {},
    role: Role = Role.STUDENT,
) {
    val dataLabel = listOf(
        "Email",
        "Tempat Lahir",
        "Tanggal Lahir",
        "Jenis Kelamin",
        "Alamat",
        "No. HP",
        "Agama",
        "NISN",
        "Wali",
    )
    val dataValues = listOf(
        profileData.email,
        profileData.birthPlace,
        profileData.birthDate,
        profileData.gender,
        profileData.address,
        profileData.phone,
        profileData.religion,
        profileData.nisn,
        profileData.parent
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = profileData.imgProfile),
            contentDescription = null,
            modifier = modifier
                .size(96.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = profileData.name,
            modifier = modifier
                .padding(vertical = 16.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
        )
        Spacer(modifier = modifier.height(24.dp))
        dataLabel.mapIndexed { index, label ->
            DetailProfileData(
                label = label,
                data = dataValues.getOrNull(index),
                modifier = modifier
            )
        }
        Spacer(modifier = modifier.height(24.dp))
        if (role == Role.STUDENT && profileData.parent == null) {
            SecondaryButton(modifier = modifier, onClick = { onClick() }, outline = true, label = "Tambah Data Orang Tua" )
        }
    }
}

@Composable
fun DetailProfileData(
    label: String = "",
    data: String? = null,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = OnBlueSecondary,
            ),
            modifier = modifier
                .weight(0.5f)
        )
        Text(
            text = data ?: "Tidak ada data",
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = DarkBlue,
                textAlign = TextAlign.End,
            ),
            modifier = modifier
                .weight(0.5f)
        )
    }
}
