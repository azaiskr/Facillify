package com.lidm.facillify.ui.siswa.riwayat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.data.RiwayatLatihanSoal
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.tracking.AbilityCard
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkGreen
import com.lidm.facillify.ui.theme.YellowOrange

@Composable
fun RiwayatScreen(
    evaluasi: String,
    saranPendidik: String,
    listRiwayat: List<RiwayatLatihanSoal>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
//        MainTopAppBar(onBackClick = { /*TODO*/ }, onProfileClick = { /*TODO*/ }, backIcon = false, profileIcon = false, sectionTitle = "Riwayat Kamu")
        LazyRow {
            items(listRiwayat.size) { index ->
                AbilityCard(
                    title = listRiwayat[index].mapel,
                    score = listRiwayat[index].nilai,
                    description = listRiwayat[index].deskripsi,
                    totalSoal = listRiwayat[index].totalSoal,
                    totalSoalBenar = listRiwayat[index].totalSoalBenar,
                    color = when (listRiwayat[index].nilai) {
                        in 0..50 -> AlertRed
                        in 51..70 -> YellowOrange
                        else -> DarkGreen
                    }
                )
            }

        }
        Text(modifier = Modifier.padding(start = 16.dp), text = "Perkembangan", fontSize = 16.sp, color = Blue, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(), text = evaluasi, fontSize = 12.sp, color = Black)

        Spacer(modifier = Modifier.height(16.dp))

        Text(modifier = Modifier.padding(start = 16.dp), text = "Saran", fontSize = 16.sp, color = Blue, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(), text = saranPendidik, fontSize = 12.sp, color = Black)

    }
}

@Composable
@Preview(showBackground = true)
fun RiwayatScreenPreview() {
    RiwayatScreen(
        evaluasi = "Evaluasi siswa sangat baik",
        saranPendidik = "Saran pendidik untuk kamu adalah belajar lebih giat lagi",
        listRiwayat = listOf(
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
    )
}