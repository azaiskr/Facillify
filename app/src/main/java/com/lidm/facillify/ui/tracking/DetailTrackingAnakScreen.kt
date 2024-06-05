package com.lidm.facillify.ui.tracking

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.R
import com.lidm.facillify.data.remote.request.CreateAssessmentForSiswaRequest
import com.lidm.facillify.data.remote.response.DetailAssesment
import com.lidm.facillify.data.remote.response.GradeHistory
import com.lidm.facillify.data.remote.response.UserProfile
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.ButtonDefault
import com.lidm.facillify.ui.components.DynamicSizeImage
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkGreen
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.ui.theme.YellowOrange
import com.lidm.facillify.ui.viewmodel.DetailTrackingAnakViewModel
import com.lidm.facillify.ui.viewmodel.SiswaRiwayatViewModel
import com.lidm.facillify.util.ResponseState
import com.lidm.facillify.util.Role
import com.lidm.facillify.util.convertToReadableDate
import kotlin.math.roundToInt

@Composable
fun DetailTrackingAnakScreen(
    onClickBack: () -> Unit,
    emailStudent: String,
    userRole: Role,
    context: Context = LocalContext.current
) {
    //viewmodel
    val siswaRiwayatViewModel: SiswaRiwayatViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )

    val detailTrackingAnakViewModel: DetailTrackingAnakViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )

    //state
    val preferences by detailTrackingAnakViewModel.getSession().observeAsState()
    val assessmentState by siswaRiwayatViewModel.assessment.collectAsState()
    val profileState by siswaRiwayatViewModel.profileSiswa.collectAsState()
    val createdAssessmentState by detailTrackingAnakViewModel.createdAssessment.collectAsState()
    val gradeHistoryState by detailTrackingAnakViewModel.gradeSiswa.collectAsState()

    //launchedeffect
    LaunchedEffect(Unit) {
        siswaRiwayatViewModel.getAssessment(emailStudent)
        siswaRiwayatViewModel.getProfileSiswa(emailStudent)
        detailTrackingAnakViewModel.getGradeHistoryStudent(emailStudent)
    }

    //CEK STATE IS LOADING OR NOT
    // Check if any state is loading
    val isLoading = assessmentState is ResponseState.Loading || profileState is ResponseState.Loading || gradeHistoryState is ResponseState.Loading

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LoadingScreen()
        }
    } else {
        // Do something while loading
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier.background(Blue)
            ) {
                // Top bar in the foreground
                TopBarDetailAnak(
                    onClickBack = { onClickBack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth(),
                    titleBar = if (userRole == Role.PARENT) "Perkembangan Anak" else "Perkembangan Siswa"
                )
            }

            //TODO MEMBUAT GET ACCOUNT
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {

                when (profileState) {
                    is ResponseState.Loading -> {

                    }
                    is ResponseState.Error -> {

                    }
                    is ResponseState.Success -> {
                        val profile = (profileState as ResponseState.Success<UserProfile>).data

                        Box{
                            // Draw the circle in the background
                            Canvas(modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.TopCenter)
                                .offset { IntOffset(215.dp.roundToPx(), -120.dp.roundToPx()) }
                            ) {
                                drawCircle(color = Blue, radius = 300.dp.toPx())
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DynamicSizeImage(
                                modifier = Modifier.size(96.dp), imageUrl = profile.profile_image_url.toString(),
                                bearerToken = preferences?.token.toString())
                            Column {
                                Text(
                                    text = "Nama Anak",
                                    fontSize = 12.sp,
                                    color = YellowOrange,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = profile.name, fontSize = 16.sp, color = Color.White, maxLines = 1)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "NISN",
                                    fontSize = 12.sp,
                                    color = YellowOrange,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = profile.nisn.toString(), fontSize = 16.sp, color = Color.White, maxLines = 1)
                            }
                        }
                    }
                }

                when (gradeHistoryState) {
                    is ResponseState.Loading -> {

                    }
                    is ResponseState.Error -> {

                    }
                    is ResponseState.Success -> {
                        val gradeHistory = (gradeHistoryState as ResponseState.Success<List<GradeHistory>>).data

                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Riwayat Nilai Siswa",
                            fontSize = 16.sp,
                            color = Blue,
                            fontWeight = FontWeight.SemiBold
                        )

                        LazyRow {
                            items(gradeHistory.size) { index ->
                                if (gradeHistory[index].quiz_title.isEmpty() && gradeHistory.isEmpty()) {
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
                                        description = convertToReadableDate(gradeHistory[index].submit_time) ,
                                        totalSoal = gradeHistory[index].num_questions,
                                        totalSoalBenar = gradeHistory[index].correct_answers,
                                        color =
                                        when (gradeHistory[index].grade) {
                                            in 0f..50f -> {
                                                AlertRed
                                            }
                                            in 51f..70f -> {
                                                YellowOrange
                                            }
                                            else -> {
                                                DarkGreen
                                            }
                                        }
                                    )
                                }
                            }

                        }

                    }
                }

                //FOR PARENT GET ASSESSMENT SISWA
                if (userRole == Role.PARENT){
                    when (assessmentState) {
                        is ResponseState.Loading -> {
                            // Show loading state
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                        is ResponseState.Error -> {
                            val error = (assessmentState as ResponseState.Error).error
                            Log.e("DetailTrackingAnakSreen", "Error: $error")
                        }
                        is ResponseState.Success -> {
                            val data = (assessmentState as ResponseState.Success<DetailAssesment>).data
                            val evaluasi = data.evaluation
                            val saranPendidik = data.suggestion

                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                text = "Evaluasi Siswa",
                                fontSize = 16.sp,
                                color = Blue,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp)
                                    .fillMaxWidth(),
                                text = evaluasi,
                                fontSize = 12.sp,
                                color = Black
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                text = "Saran Pendidik",
                                fontSize = 16.sp,
                                color = Blue,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp)
                                    .fillMaxWidth(),
                                text = saranPendidik,
                                fontSize = 12.sp,
                                color = Black
                            )
                        }
                    }
                }

                //FOR TEACHER CREATE ASSESSMENT FOR SISWA
                if (userRole == Role.TEACHER){

                    when (assessmentState) {
                        is ResponseState.Loading -> {
                            Log.d("DetailTrackingAnakSreen", "Loading")
                        }
                        is ResponseState.Error -> {
                            Log.d("DetailTrackingAnakSreen", "Error")
                        }
                        is  ResponseState.Success -> {
                            val data = (assessmentState as ResponseState.Success<DetailAssesment>).data
                            val evaluasi = data.evaluation
                            val saranPendidik = data.suggestion

                            var evaluasiguru by remember { mutableStateOf(evaluasi) }
                            var saranguru by remember { mutableStateOf(saranPendidik) }

                            when (profileState) {
                                is ResponseState.Loading -> {

                                }
                                is ResponseState.Error -> {

                                }
                                is ResponseState.Success -> {

                                    val emailSiswa = (profileState as ResponseState.Success<UserProfile>).data.email

                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text = "Evaluasi Siswa",
                                        fontSize = 16.sp,
                                        color = Blue,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))

                                    OutlinedTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        value = evaluasiguru,
                                        onValueChange = { evaluasiguru = it },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedBorderColor = SecondaryBlue,
                                            focusedBorderColor = Blue,
                                            focusedTextColor = Blue,
                                            unfocusedTextColor = Blue,
                                        ),
                                        placeholder = {
                                            Text(
                                                text = "Evaluasi perkembangan siswa",
                                                color = SecondaryBlue,
                                                fontSize = 12.sp
                                            )
                                        }
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text = "Saran Pendidik",
                                        fontSize = 16.sp,
                                        color = Blue,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    OutlinedTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        value = saranguru,
                                        onValueChange = { saranguru = it },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedBorderColor = SecondaryBlue,
                                            focusedBorderColor = Blue,
                                            focusedTextColor = Blue,
                                            unfocusedTextColor = Blue,
                                        ),
                                        placeholder = {
                                            Text(
                                                text = "Saran pemaksimalan potensi dan perkembangan siswa",
                                                color = SecondaryBlue,
                                                fontSize = 12.sp
                                            )
                                        }
                                    )

                                    //data want to create
                                    val createdAssessment = CreateAssessmentForSiswaRequest(
                                        email = emailSiswa,
                                        evaluation = evaluasiguru,
                                        suggestion = saranguru
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        ButtonDefault(modifier = Modifier, text = "Simpan", onClick = {
                                            //TODO: LOGIC FOR SAVE SARAN DAN EVALUASI

                                            detailTrackingAnakViewModel.createAssessment(createdAssessment)
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    when (createdAssessmentState) {
        is ResponseState.Loading -> {
            Log.d("DetailTrackingAnakSreen", "Loading")
        }
        is ResponseState.Error -> {
            Log.d("DetailTrackingAnakSreen", "Error")
            Toast.makeText(context, "Gagal Menyimpan", Toast.LENGTH_LONG).show()
        }
        is ResponseState.Success -> {
            Log.d("DetailTrackingAnakSreen", "Success")
            Toast.makeText(context, "Berhasil Menyimpan", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun TopBarDetailAnak(
    onClickBack: () -> Unit = {},
    modifier: Modifier = Modifier,
    titleBar: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.clickable { onClickBack() },
            painter = painterResource(id = R.drawable.round_arrow_back),
            contentDescription = "back",
            tint = Color.White,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = titleBar, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.White)
    }
}

@Composable
fun AbilityCard(
    title: String,
    score: Int,
    description: String,
    totalSoal: Int,
    totalSoalBenar: Int,
    color: Color = DarkGreen
) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SecondaryBlue) // Light blue background
            .padding(16.dp)
            .width(120.dp),
        color = Color.Transparent
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
            ) {
                CircularProgressIndicator(
                    progress = score / 100f,
                    strokeWidth = 8.dp,
                    color = color, // Teal color
                    modifier = Modifier.fillMaxSize(),
                    strokeCap = StrokeCap.Round
                )
                Text(
                    text = score.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = color // Teal color
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$totalSoalBenar/$totalSoal",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = color,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Blue, // Dark blue color
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                color = Blue, // Dark blue color
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AbilityCardPreview() {
    AbilityCard(
        title = "Kemampuan Baca",
        score = 12.312f.roundToInt(),
        description = "Kemampuan membaca anak sudah sangat baik",
        totalSoal = 100,
        totalSoalBenar = 90
    )
}