package com.lidm.facillify.ui.guru.latihansoal

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.data.ItemSoal
import com.lidm.facillify.ui.components.InputTextFieldDefault
import com.lidm.facillify.ui.components.ItemSoalCard
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.SecondaryBlue

@Composable
fun TambahLationSoalGuruScreen() {
    var latihanSoal = remember { mutableStateListOf<ItemSoal>() }
    var judulSoal by remember { mutableStateOf("") }
    var deskripsiSoal by remember { mutableStateOf("") }

    //DUMMY DATA
    latihanSoal.add(ItemSoal("Soal 1", listOf("Jawaban 1", "Jawaban 2", "Jawaban 3"), "Jawaban 1"))

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            MainTopAppBar(onBackClick = { /*TODO*/ }, onProfileClick = { /*TODO*/ }, backIcon = true, profileIcon = false, sectionTitle = "Tambah Latihan Soal")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {

                InputTextFieldDefault(topText = "Judul Latihan Soal", insideText = "Masukan judul latihan soal", valueText = judulSoal, onValueChange = {judulSoal = it} )

                Spacer(modifier = Modifier.height(16.dp))

                InputTextFieldDefault(topText = "Deskripsi Materi Latihan Soal", insideText = "Masukan deskripsi materi latihan soal", valueText = deskripsiSoal, onValueChange = {deskripsiSoal = it} )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(latihanSoal.size){index ->
                        ItemSoalCard(
                            soal = latihanSoal[index].soal,
                            jawaban = latihanSoal[index].jawaban ,
                            jawabanBenar = latihanSoal[index].jawabanBenar,
                            onEditClick = { /*TODO*/ }) {
                            //TODO LOGIC FOR DELETE ITEM AND EDIT
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = SecondaryBlue), shape = RoundedCornerShape(8.dp)) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add soal", tint = Blue )
                }
            }
        }

        TimeAndUploadButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            duration = ""/*TODO*/,
            onClickUpload = { /*TODO*/ },
            durationOnChange = { /*TODO*/ }
        )
    }
}

@Composable
fun TimeAndUploadButton(
    modifier: Modifier = Modifier,
    onClickUpload: () -> Unit,
    duration: String,
    durationOnChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Waktu Pengerjaan", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Blue)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //INPUT FIELD
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    value = duration,
                    onValueChange = durationOnChange, colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedTextColor = Blue,
                    unfocusedTextColor = Blue,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ), placeholder = {
                    Text(text = "Dalam Menit", fontSize = 16.sp, color = SecondaryBlue)
                }, shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp)
                )

                //BUTTON
                Button(
                    onClick = { onClickUpload() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue
                    ),
                    shape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp),
                    modifier = Modifier.height(56.dp)
                )
                    {
                    Text(text = "Unggah", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TambahLationSoalGuruScreenPreview() {
    TambahLationSoalGuruScreen()
}

@Composable
@Preview(showBackground = true)
fun TimeAndUploadButtonPreview() {
    var duration by remember { mutableStateOf("") }
    TimeAndUploadButton(
        duration = duration,
        onClickUpload = { /*TODO*/ },
        durationOnChange = { /*TODO*/ }
    )
}