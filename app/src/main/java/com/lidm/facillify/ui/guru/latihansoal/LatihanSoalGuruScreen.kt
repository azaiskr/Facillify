package com.lidm.facillify.ui.guru.latihansoal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidm.facillify.data.LatihanSoal
import com.lidm.facillify.ui.components.CardLatihanSoalGuru
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.OnBlueSecondary

@Composable
fun BaseLatihanSoalGuruScreen(

) {


    LatihanSoalGuruScreen()
}

@Composable
fun LatihanSoalGuruScreen(
    listSoal: List<LatihanSoal> = emptyList()
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                items(listSoal.size) { index ->
                    CardLatihanSoalGuru(
                        judulSoal = listSoal[index].JudulLatihanSoal,
                        deskripsiSoal = listSoal[index].DeskripsiLatihanSoal,
                        waktuSoal = listSoal[index].waktuLatihanSoal
                    ) {
                        //TODO LOGIC FOR DELETE ITEM
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun LatihanSoalGuruScreenPreview() {
    LatihanSoalGuruScreen(
        listSoal = listOf(
            LatihanSoal(
                JudulLatihanSoal = "Latihan 1",
                DeskripsiLatihanSoal = "Latihan 1",
                waktuLatihanSoal = 20,
                Soal = listOf()
            ),
            LatihanSoal(
                JudulLatihanSoal = "Latihan 2",
                DeskripsiLatihanSoal = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                waktuLatihanSoal = 45,
                Soal = listOf()
            )
        )
    )
}