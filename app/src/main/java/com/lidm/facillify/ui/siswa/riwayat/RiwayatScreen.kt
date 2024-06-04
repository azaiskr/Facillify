package com.lidm.facillify.ui.siswa.riwayat

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.lidm.facillify.data.RiwayatLatihanSoal
import com.lidm.facillify.data.remote.response.DetailAssesment
import com.lidm.facillify.data.remote.response.GradeHistory
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkGreen
import com.lidm.facillify.ui.theme.YellowOrange
import com.lidm.facillify.ui.tracking.AbilityCard
import com.lidm.facillify.ui.viewmodel.SiswaRiwayatViewModel
import com.lidm.facillify.util.ResponseState
import com.lidm.facillify.util.convertToReadableDate
import kotlin.math.roundToInt

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
    val gradeHistoryState by siswaRiwayatViewModel.gradeSiswa.collectAsState()
    val swipeRefreshState = remember { SwipeRefreshState(isRefreshing = false) }

    //data
    val emailUser = if (emailState is ResponseState.Success) {
        (emailState as ResponseState.Success).data
    } else {
        Log.e( "KonsultasiForumScreen", "Failed to fetch email user")// Default value or handle loading/error state
    }

    //launched Effect
    LaunchedEffect(emailUser) {
        Log.d("RiwayatScreen", "Email user: $emailUser")
        siswaRiwayatViewModel.getAssessment(emailUser.toString())
        siswaRiwayatViewModel.getGradeHistoryStudent(emailUser.toString())
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (emailState is ResponseState.Loading || assessmentState is ResponseState.Loading || gradeHistoryState is ResponseState.Loading) {
            LoadingScreen()
        }
        SwipeRefresh(state = swipeRefreshState, onRefresh = { siswaRiwayatViewModel.getAssessment(emailUser.toString())
            siswaRiwayatViewModel.getGradeHistoryStudent(emailUser.toString())}) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                when (gradeHistoryState) {
                    is ResponseState.Loading -> {

                    }
                    is ResponseState.Error -> {

                    }

                    is ResponseState.Success -> {
                        val gradeHistory = (gradeHistoryState as ResponseState.Success<List<GradeHistory>>).data

                        LazyRow {
                            items(gradeHistory.size) { index ->
                                if (gradeHistory.isEmpty()){
                                    Column(
                                        modifier = Modifier
                                            .fillParentMaxHeight()
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(text = "Belum ada Riwayat")
                                    }
                                } else {
                                    AbilityCard(
                                        title = gradeHistory[index].quiz_title,
                                        score = gradeHistory[index].grade.roundToInt(),
                                        description = convertToReadableDate(gradeHistory[index].submit_time),
                                        totalSoal = gradeHistory[index].num_questions,
                                        totalSoalBenar = gradeHistory[index].correct_answers,
                                        color = when (gradeHistory[index].grade.roundToInt()) {
                                            in 0..50 -> AlertRed
                                            in 51..70 -> YellowOrange
                                            else -> DarkGreen
                                        }
                                    )
                                }
                            }

                        }
                    }
                }

                when (assessmentState) {
                    is ResponseState.Loading -> {
                        // Show loading state
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
    }

}