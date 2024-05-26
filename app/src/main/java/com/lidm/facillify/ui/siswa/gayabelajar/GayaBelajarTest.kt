package com.lidm.facillify.ui.siswa.gayabelajar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.data.local.gayaBelajarTestQuestions
import com.lidm.facillify.ui.siswa.FormSoal
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.util.Question
import kotlin.reflect.KFunction0

@Preview
@Composable
fun GayaBelajarTest(
    modifier: Modifier = Modifier,
    onNavigateToTestResult: () -> Unit = {},
) {
    val answer = remember { mutableStateListOf<String>() }
    fun onSubmit() {
        Log.d("answer", "LatihanScreen: ${answer.toList()}")
        onNavigateToTestResult()
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

    FormTest(
        questions = gayaBelajarTestQuestions,
        onSubmit = ::onSubmit,
        answer = answer,
        modifier = modifier,
    )
}

@Composable
fun FormTest(
    questions: List<Question>,
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
        FormSoal(
            modifier = modifier,
            item = questions,
            onSubmit = onSubmit,
            answer = answer,
        )
    }
}
