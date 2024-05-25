package com.lidm.facillify.ui.guru.materibelajar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidm.facillify.ui.components.InputTextFieldDefault
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.theme.Blue

@Composable
fun TambahMateriBelajarScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {

            //State
            var videoUrl by remember { mutableStateOf("") }
            var judulMateri by remember { mutableStateOf("") }
            var deskripsiMateri by remember { mutableStateOf("") }

            MainTopAppBar(onBackClick = { /*TODO*/ }, onProfileClick = { /*TODO*/ }, profileIcon = false, sectionTitle = "Tambah Materi Belajar", backIcon = true)
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                InputTextFieldDefault(topText = "Video Url", insideText = "Masukan alamat video url youtube", valueText = videoUrl , onValueChange = { videoUrl = it})
                Spacer(modifier = Modifier.height(16.dp))
                InputTextFieldDefault(topText = "Judul Materi", insideText = "Masukan judul materi", valueText = judulMateri , onValueChange = { judulMateri = it})
                Spacer(modifier = Modifier.height(16.dp))
                InputTextFieldDefault(topText = "Deskripsi Materi", insideText = "Masukan deskripsi materi", valueText = deskripsiMateri , onValueChange = { deskripsiMateri = it})
            }
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(16.dp)
                .height(56.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            //TODO LOGIC FOR ADD MATERI
            //TODO CHANGE TEXT TO ICON
            Text(text = "Tambah Materi")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TambahMateriBelajarScreenPreview() {
    TambahMateriBelajarScreen()
}