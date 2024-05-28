package com.lidm.facillify.ui.siswa.belajar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.data.local.LatihanItem
import com.lidm.facillify.data.local.QuizResult
import com.lidm.facillify.data.local.dataLatihan
import com.lidm.facillify.ui.components.CountDownTimer
import com.lidm.facillify.ui.components.DialogConfirm
import com.lidm.facillify.ui.siswa.FormSoal
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.util.getGrade

@Composable
fun LatihanScreen(
    modifier: Modifier,
    latihanId: Int,
) {
    val latihan = dataLatihan.find { it.id == latihanId } !!
    val answer = remember { mutableStateListOf<String>() }
    var showDialog by remember { mutableStateOf(false) }


    fun onSubmit () {
        showDialog = true
    }

    fun submitAnswer() {
        val result = getGrade(answer, latihan.answeKey)
        Log.d("answer", "LatihanScreen: ${answer.toList()}")
        Log.d("grade", "LatihanScreen: ${result.grade} : correct : ${result.correctAnswer}")
    }




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

    FormLatihan(
        latihanItem = latihan,
        answer = answer,
        onSubmit = ::onSubmit,
        submitAnswer = ::submitAnswer,
        totalTimeMinutes = latihan.waktu
    )

    if (showDialog) {
        DialogConfirm(
            onDismiss = { showDialog = false },
            onConfirm = {
                showDialog = false
                submitAnswer()
            },
            title = "Kumpulkan Jawaban?",
            msg ="Apakah kamu sudah yakin dengan semua jawabanmu? Pastikan semua soal telah terjawab ya.",
            confirmLabel = "Kumpulkan",
            dismissLabel = "Batal"
        )
    }
}

@Composable
fun FormLatihan(
    latihanItem: LatihanItem,
    modifier: Modifier = Modifier,
    answer: MutableList<String>,
    onSubmit: () -> Unit,
    submitAnswer: () -> Unit,
    totalTimeMinutes: Int,
) {

    val totalSeconds = totalTimeMinutes * 60

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = latihanItem.judul,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = DarkBlue
            ),
            modifier = modifier
                .padding(top = 24.dp, bottom = 8.dp)
        )
        CountDownTimer(
            totalTimeSeconds = totalSeconds,
            modifier = modifier,
            onTimerFinished = submitAnswer
        )
        Spacer(modifier = modifier.padding(8.dp))
        FormSoal(
            modifier = modifier,
            item = latihanItem.questions,
            answer = answer,
            onSubmit = onSubmit,
            totalTimeMinutes = totalTimeMinutes
        )
    }
}
