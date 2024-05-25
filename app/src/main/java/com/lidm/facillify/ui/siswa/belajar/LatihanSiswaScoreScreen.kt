package com.lidm.facillify.ui.siswa.belajar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidm.facillify.ui.components.MainButton
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.Green
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.ui.theme.Yellow

data class LaithanResult(
    val name: String,
    val correct: Int,
    val wrong: Int,
    val score: Int,
    val timeTaken: Long,
    val comment: String,
)

val dummyResultQuiz = LaithanResult(
    name = "Latihan 1",
    correct = 17,
    wrong = 3,
    score = 75,
    timeTaken = 20,
    comment = "Perolehan nilai kamu menunjukkan bahwa kamu telah memahami dengan baik."
)

@Composable
fun LatihanSiswaScoreScreen(

) {

    LatihanResult(dummyResultQuiz)
}

@Composable
fun LatihanResult(
    quizResult: LaithanResult,
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.5f)
                .clip(RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
        ) {
            drawRoundRect(
                color = Blue,
                topLeft = Offset(0f, -50f),
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Nilai akhir",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Blue,
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(SecondaryBlue)
                    .padding(vertical = 8.dp, horizontal = 48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Selamat kamu telah menyelesaikan",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = SecondaryBlue,
            )
            Text(
                text = quizResult.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = SecondaryBlue,
            )
            Spacer(modifier = Modifier.height(56.dp))
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { quizResult.score.toFloat()*0.01f },
                    modifier = Modifier.size(180.dp),
                    color = when (quizResult.score) {
                        in 51..75 -> Yellow
                        in 75..100 -> Green
                        else -> AlertRed
                    },
                    strokeWidth = 12.dp,
                    strokeCap = StrokeCap.Round
                )
                Text(
                    text = "${quizResult.score}",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Blue,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SecondaryBlue)
                        .padding(32.dp)
                )
            }
        }
        Column (
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight(fraction = 0.5f)
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row (
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column (
                ){
                    Text (
                        text = "Jawaban benar",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Blue,
                    )
                    Text (
                        text = quizResult.correct.toString() + " soal",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = Blue,
                    )
                }
                Column (
                ){
                    Text (
                        text = "Akurasi",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Blue,
                    )
                    Text (
                        text = "${quizResult.score} %",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = Blue,
                    )
                }
            }

            Column (
                modifier = Modifier.padding(horizontal = 16.dp)
            ){
                Text (
                    text = "Waktu pengerjaan",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Blue,
                )
                Text (
                    text = "${quizResult.timeTaken} mnt",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = Blue,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text (
                text = quizResult.comment,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = Blue,
                textAlign = TextAlign.Center
            )
            MainButton(
                modifier = Modifier,
                onClick = { /*TODO*/ },
                labelText = "Kembali"
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LatihanSiswaScoreScreenPreview() {
    LatihanSiswaScoreScreen()
}