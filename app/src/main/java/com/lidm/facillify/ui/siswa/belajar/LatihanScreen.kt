package com.lidm.facillify.ui.siswa.belajar

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.data.local.LatihanItem
import com.lidm.facillify.data.local.dataLatihan
import com.lidm.facillify.data.local.hasilLatihan
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.CountDownTimer
import com.lidm.facillify.ui.components.DialogConfirm
import com.lidm.facillify.ui.siswa.FormSoal
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.viewmodel.LatihanSiswaViewModel
import kotlinx.coroutines.delay

@Composable
fun LatihanScreen(
    modifier: Modifier,
    latihanId: Int,
    onNavigateToTestresult: (Int) -> Unit,
) {
    val latihan = dataLatihan.find { it.id == latihanId } !!
    val answer = remember { mutableStateListOf<String>() }

    val latihanSiswaViewModel: LatihanSiswaViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context = LocalContext.current)
    )
    var showDialog by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }

    fun onSubmit () {
        showDialog = true
    }

    fun submitAnswer() {
        isRunning = false
        val result = latihanSiswaViewModel.getGrade(
            latihanId = latihanId,
            answer = answer,
            keys = latihan.answeKey,
            timetaken = elapsedTime,
        )
        if (hasilLatihan.find { it.idLatihan == latihanId } == null) {
            hasilLatihan.add(result)
        } else {
            hasilLatihan.find { it.idLatihan == latihanId }?.let {
                it.grade = result.grade
                it.correctAnswer = result.correctAnswer
                it.timeTaken = result.timeTaken
            }
        }
        onNavigateToTestresult(latihanId)
        Log.d("grade", "LatihanScreen: ${result.grade} > correct : ${result.correctAnswer} > time : ${result.timeTaken} > ${hasilLatihan.size}" )
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
        totalTimeMinutes = latihan.waktu,

    )

    LaunchedEffect (key1 = isRunning) {
        if (isRunning) {
            delay(1000L)
            elapsedTime++
        }

    }

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

    androidx.activity.compose.BackHandler (enabled = true) {

    }
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, _ ->
//            val activity = context as? Activity
//            activity?.moveTaskToBack(false)
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }

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
