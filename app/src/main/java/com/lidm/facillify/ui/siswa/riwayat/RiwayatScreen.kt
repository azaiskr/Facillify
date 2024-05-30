package com.lidm.facillify.ui.siswa.riwayat

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.data.RiwayatLatihanSoal
import com.lidm.facillify.data.remote.response.DetailAssesment
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkGreen
import com.lidm.facillify.ui.theme.YellowOrange
import com.lidm.facillify.ui.tracking.AbilityCard
import com.lidm.facillify.ui.viewmodel.SiswaRiwayatViewModel
import com.lidm.facillify.util.ResponseState

@Composable
fun RiwayatScreen(
    context: Context = LocalContext.current
) {
    //viewmodel
    val siswaRiwayatViewModel: SiswaRiwayatViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )

    //state
    val emailState by siswaRiwayatViewModel.emailUser.collectAsState()
    val assessmentState by siswaRiwayatViewModel.assessment.collectAsState()

    //data
    val emailUser = if (emailState is ResponseState.Success) {
        (emailState as ResponseState.Success).data
    } else {
        Log.e( "KonsultasiForumScreen", "Failed to fetch email user")// Default value or handle loading/error state
    }

    //launched Effect
    LaunchedEffect(emailUser) {
        if (emailUser != null) {
            Log.d("RiwayatScreen", "Email user: $emailUser")
            siswaRiwayatViewModel.getAssessment(emailUser.toString())
        }
    }

    //dummy data
    var listRiwayat: List<RiwayatLatihanSoal> = listOf(
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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

        when (assessmentState) {
            is ResponseState.Loading -> {
                // Show loading state
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ResponseState.Error -> {
                // Show error state
                val error = (assessmentState as ResponseState.Error).error
                Log.e("RiwayatScreen", "Error: $error")
            }
            is ResponseState.Success -> {
                val data = (assessmentState as ResponseState.Success<DetailAssesment>).data
                val evaluasi = data.evaluation
                val saranPendidik = data.suggestion

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
    }
}

@Composable
@Preview(showBackground = true)
fun RiwayatScreenPreview() {
    RiwayatScreen(

    )
}