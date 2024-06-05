package com.lidm.facillify.ui.siswa.gayabelajar

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.data.local.paketMateri.SoalTestGayaBelajar
import com.lidm.facillify.data.remote.request.LearningStyleRequest
import com.lidm.facillify.model.QuizGayaBelajar
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.siswa.FormSoalGayaBelajar
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.viewmodel.GayaBelajarViewModel
import com.lidm.facillify.ui.viewmodel.ProfileViewModel
import com.lidm.facillify.util.ResponseState
import com.lidm.facillify.util.calculateLearningStyle

@Preview
@Composable
fun GayaBelajarTest(
    modifier: Modifier = Modifier,
    onNavigateToTestResult: () -> Unit = {},
    context: Context = LocalContext.current
) {
    //viewmodel
    val gayaBelajarViewModel: GayaBelajarViewModel = viewModel(
        factory = ViewModelFactory.getInstance(
            context.applicationContext
        )
    )

    val profileViewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )

    //state
    val answer = remember { mutableStateListOf<String>() }
    val learningStyleState by gayaBelajarViewModel.learningStyle.collectAsState()

    val preferences by profileViewModel.getSession().observeAsState()

    LaunchedEffect(Unit) {
        profileViewModel.getSession()
    }

    fun onSubmit() {
        if (answer.size != SoalTestGayaBelajar.size || answer.any { it.isBlank() }){
            Toast.makeText(context, "Jawaban belum lengkap", Toast.LENGTH_SHORT).show()
            return
        } else {
            Log.d("answer", "LatihanScreen: ${answer.toList()}")
            val result = calculateLearningStyle(answer)
            Log.d("result", "GayaBelajarTest: $result")

            val siswaResult = LearningStyleRequest(
                student_email = preferences?.email.toString(),
                learning_style = result,
            )

            gayaBelajarViewModel.putLearningStye(siswaResult)

            when (learningStyleState) {
                is ResponseState.Loading -> {
                    Log.d("GayaBelajarTest", "Loading")
                }
                is ResponseState.Success -> {
                    Log.d("GayaBelajarTest", "Success")
                }
                is ResponseState.Error -> {
                    Log.d("GayaBelajarTest", "Error")
                }
            }
            onNavigateToTestResult()
        }
    }

    FormTest(
        onSubmit = {onSubmit()},
        answer = answer,
        modifier = modifier,
    )
}

@Composable
fun FormTest(
    questions: List<QuizGayaBelajar> = SoalTestGayaBelajar,
    onSubmit: () -> Unit,
    answer: MutableList<String>,
    modifier: Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tes Gaya Belajar",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            color = DarkBlue,
            modifier = modifier
                .padding(vertical = 24.dp)
        )
        FormSoalGayaBelajar(
            modifier = modifier,
            item = questions,
            onSubmit = onSubmit,
            answer = answer,
        )
    }
}
