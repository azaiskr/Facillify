package com.lidm.facillify.ui.tracking

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.data.RiwayatLatihanSoal
import com.lidm.facillify.ui.components.ButtonDefault
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkGreen
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.ui.theme.YellowOrange
import com.lidm.facillify.util.Role

@Composable
fun DetailTrackingScreen(
    onClickBack: () -> Unit,
    role: Role,
) {
    //viewModel
    //state

    DetailTrackingAnakScreen(
        onClickBack = onClickBack,
        userRole = role,
        imagePainter = painterResource(id = R.drawable.pp_deafult),
        name = "Winko Adi",
        number = "310128",
        saranPendidik = "Anu anu aja",
        evalusiSiswa = "Nuna hinu",
        listRiwayat = listOf(
            RiwayatLatihanSoal(
                nilai = 90,
                deskripsi = "Kemampuan membaca anak sudah sangat baik",
                mapel = "Kemampuan Baca",
                totalSoal = 100,
                totalSoalBenar = 70
            )
        )
    )
}

@Composable
fun DetailTrackingAnakScreen(
    onClickBack: () -> Unit,
    userRole: Role,
    imagePainter: Painter,
    name: String,
    number: String,
    saranPendidik: String,
    evalusiSiswa: String,
    listRiwayat: List<RiwayatLatihanSoal>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var evaluasi by remember { mutableStateOf("") }
        var saran by remember { mutableStateOf("") }

        Box {
            // Draw the circle in the background
            Canvas(modifier = Modifier
                .size(20.dp)
                .align(Alignment.TopCenter)
                .offset { IntOffset(0, -300.dp.roundToPx()) }
            ) {
                drawCircle(color = Blue, radius = 500.dp.toPx())
            }

            // Top bar in the foreground
            TopBarDetailAnak(
                onClickBack = onClickBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth(),
                titleBar = if (userRole == Role.PARENT) "Perkembangan Anak" else "Perkembangan Siswa"
            )
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
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Evaluasi Siswa",
            fontSize = 16.sp,
            color = Blue,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        //PARENT
        if (userRole == Role.PARENT) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                text = evalusiSiswa.ifEmpty { "Belum ada evaluasi" },
                fontSize = 12.sp,
                color = Black
            )
        }

        //TEACHER
        if (userRole == Role.TEACHER) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = evaluasi,
                onValueChange = { evaluasi = it },
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
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Saran Pendidik",
            fontSize = 16.sp,
            color = Blue,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        //PARENNT
        if (userRole == Role.PARENT) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                text = saranPendidik.ifEmpty { "Belum ada saran pendidik" },
                fontSize = 12.sp,
                color = Black
            )
        }

        //TEACHER
        if (userRole == Role.TEACHER) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = saran,
                onValueChange = { saran = it },
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

            //Button
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
fun DetailTrackingAnakScreenPreview() {
    DetailTrackingAnakScreen(
        onClickBack = {},
        userRole = Role.TEACHER,
        imagePainter = painterResource(id = R.drawable.pp_deafult),
        name = "Rizky Wirawan Sedoyo",
        number = "1",
        saranPendidik = "Anak sudah sangat baik dalam belajar",
        evalusiSiswa = "Anak sudah sangat baik dalam belajar",
        listRiwayat = listOf(
            RiwayatLatihanSoal(
                nilai = 90,
                deskripsi = "Kemampuan membaca anak sudah sangat baik",
                mapel = "Kemampuan Baca",
                totalSoal = 100,
                totalSoalBenar = 90
            ),
            RiwayatLatihanSoal(
                nilai = 80,
                deskripsi = "Kemampuan menulis anak sudah sangat baik",
                mapel = "Kemampuan Tulis",
                totalSoal = 100,
                totalSoalBenar = 80
            ),
        )
    )
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