package com.lidm.facillify.ui.tracking

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
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
import com.lidm.facillify.data.RiwayatLatihanSoal
import com.lidm.facillify.data.remote.response.DetailAssesment
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.ButtonDefault
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkGreen
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.ui.theme.YellowOrange
import com.lidm.facillify.ui.viewmodel.SiswaRiwayatViewModel
import com.lidm.facillify.util.ResponseState
import com.lidm.facillify.util.Role

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


    //state
    val assessmentState by siswaRiwayatViewModel.assessment.collectAsState()

    //launchedeffect
    LaunchedEffect(userRole == Role.PARENT) {
        siswaRiwayatViewModel.getAssessment(emailStudent)
    }


    //TODO GET PROFILE
    val imagePainter: Painter = painterResource(id = R.drawable.pp_deafult)
    val name: String = "Winko Adi"
    val number: String = "310128"
    val listRiwayat: List<RiwayatLatihanSoal> = listOf(
        RiwayatLatihanSoal(
            nilai = 90,
            deskripsi = "Kemampuan membaca anak sudah sangat baik",
            mapel = "Kemampuan Baca",
            totalSoal = 100,
            totalSoalBenar = 70
        )
    )

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

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
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
                Image(
                    painter = imagePainter,
                    contentDescription = "photo profile",
                    modifier = Modifier.size(96.dp)
                )
                Column {
                    Text(
                        text = "Nama Anak",
                        fontSize = 12.sp,
                        color = YellowOrange,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = name, fontSize = 16.sp, color = Color.White, maxLines = 1)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "NISN",
                        fontSize = 12.sp,
                        color = YellowOrange,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = number, fontSize = 16.sp, color = Color.White, maxLines = 1)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Riwayat Nilai Siswa",
                fontSize = 16.sp,
                color = Blue,
                fontWeight = FontWeight.SemiBold
            )
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

                var evaluasiguru by remember { mutableStateOf("") }
                var saranguru by remember { mutableStateOf("") }


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


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    ButtonDefault(modifier = Modifier, text = "Simpan", onClick = {
                        //TODO: LOGIC FOR SAVE SARAN DAN EVALUASI
                    })
                }

            }
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
        score = 90,
        description = "Kemampuan membaca anak sudah sangat baik",
        totalSoal = 100,
        totalSoalBenar = 90
    )
}