package com.lidm.facillify.ui.siswa.belajar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.data.remote.request.SubmitQuizAnswerRequest
import com.lidm.facillify.data.remote.response.Quiz
import com.lidm.facillify.data.remote.response.QuizDetailResponse
import com.lidm.facillify.data.remote.response.SubmitQuizResponse
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.CountDownTimer
import com.lidm.facillify.ui.components.DialogConfirm
import com.lidm.facillify.ui.responseStateScreen.ErrorScreen
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.siswa.FormSoal
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.viewmodel.LatihanSiswaViewModel
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.delay
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LatihanScreen(
    modifier: Modifier,
    latihanId: String,
    onBackClicked: () -> Unit,
) {
    val latihanSiswaViewModel: LatihanSiswaViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context = LocalContext.current)
    )
    val quizResponse by latihanSiswaViewModel.quiz.collectAsState()
    val emailResponse by latihanSiswaViewModel.emailUser.collectAsState()
    val quizResultResponse by latihanSiswaViewModel.quizResult.collectAsState()
    val answer = remember { mutableStateListOf<String>() }

    var timeStamp by remember { mutableStateOf(Instant.now()) }
    var showDialog by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var emailUser by remember { mutableStateOf("") }
    var quizTitle by remember { mutableStateOf("") }

    if (emailResponse is ResponseState.Success) {
        emailUser = (emailResponse as ResponseState.Success<String>).data
    }

    val dataRequested = SubmitQuizAnswerRequest(
        email = emailUser,
        answers = answer,
    )

    LaunchedEffect(Unit) {
        latihanSiswaViewModel.getQuiz(latihanId)
        latihanSiswaViewModel.getEmailuser()
    }

    fun onSubmit() {
        showDialog = true
    }

    fun submitAnswer() {
        isRunning = false
        latihanSiswaViewModel.submitQuiz(latihanId, dataRequested)
        Log.d("Latihan Screen", "Quiz Result: $quizResultResponse")
    }


    when (quizResultResponse) {
        is ResponseState.Loading -> {
            when(quizResponse) {
                is ResponseState.Loading -> LoadingScreen()
                is ResponseState.Success -> {
                    val quizDetail = (quizResponse as ResponseState.Success<QuizDetailResponse>).data
                    val quiz = quizDetail.result
                    quizTitle = quiz.title
                    timeStamp = Instant.now()
                    FormLatihan(
                        latihanItem = quiz,
                        answer = answer,
                        onSubmit = ::onSubmit,
                        submitAnswer = ::submitAnswer,
                        totalTimeMinutes = quiz.time,
                    )
                    LaunchedEffect(key1 = isRunning) {
                        if (isRunning) {
                            delay(1000L)
                            elapsedTime++
                        }

                    }
                }
                is ResponseState.Error -> {
                    ErrorScreen(
                        onTryAgain = { latihanSiswaViewModel.getQuiz(quizId = latihanId) }
                    )
                }
            }
        }
        is ResponseState.Success -> {
            val quizResult = (quizResultResponse as ResponseState.Success<SubmitQuizResponse>).data
            Log.d("LatihanScreen", "Quiz Result: $quizResult")
            LatihanResultScreen(
                quizResult = quizResult.data,
                quizdata = quizTitle,
                onBackClicked = onBackClicked
            )
        }
        is ResponseState.Error -> {
            ErrorScreen(
                onTryAgain = { latihanSiswaViewModel.getQuiz(quizId = latihanId) }
            )
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
            msg = "Apakah kamu sudah yakin dengan semua jawabanmu? Pastikan semua soal telah terjawab ya.",
            confirmLabel = "Kumpulkan",
            dismissLabel = "Batal"
        )
    }

    androidx.activity.compose.BackHandler(enabled = true) {
    }
}

@Composable
fun FormLatihan(
    latihanItem: Quiz,
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
            text = latihanItem.title,
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
        )
    }
}
