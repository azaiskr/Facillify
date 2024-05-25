package com.lidm.facillify.ui.siswa

import androidx.compose.runtime.Composable
import com.lidm.facillify.data.RiwayatLatihanSoal
import com.lidm.facillify.ui.siswa.riwayat.RiwayatScreen

val dummyData = listOf (
    RiwayatLatihanSoal(
        mapel = "Matematika",
        deskripsi = "Latihan soal matematika",
        nilai = 90,
        totalSoal = 100,
        totalSoalBenar = 90
    ),
    RiwayatLatihanSoal(
        mapel = "Bahasa Indonesia",
        deskripsi = "Latihan soal bahasa indonesia",
        nilai = 70,
        totalSoal = 100,
        totalSoalBenar = 70
    ),
    RiwayatLatihanSoal(
        mapel = "IPA",
        deskripsi = "Latihan soal IPA",
        nilai = 40,
        totalSoal = 100,
        totalSoalBenar = 40
    )
)

@Composable
fun SiswaRiwayatScreen(

){
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

    RiwayatScreen(evaluasi = "Loremipsum", saranPendidik = "Ini Saran", listRiwayat = dummyData )
}