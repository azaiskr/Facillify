package com.lidm.facillify.ui.siswa

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lidm.facillify.ui.components.DialogConfirm
import com.lidm.facillify.ui.components.MainButton
import com.lidm.facillify.ui.components.QuestionItem
import com.lidm.facillify.util.Question
import kotlinx.coroutines.delay

@Composable
fun FormSoal(
    modifier : Modifier,
    item: List<Question>,
    onSubmit: () -> Unit,
    answer: MutableList<String>,
    submitAnswer: () -> Unit,
    totalTimeMinutes: Int = 60,
){
    val remainingTime = remember { mutableIntStateOf(totalTimeMinutes * 60) }
    val minutes = remainingTime.intValue / 60
    val seconds = remainingTime.intValue % 60

//    LaunchedEffect(Unit) {
//        while (remainingTime.intValue > 0) {
//            delay(1000L)
//            remainingTime.intValue -= 1
//        }
//        submitAnswer() // Automatically submit when timer ends
//    }
    if (answer.isEmpty()) {
        repeat(item.size) {
            answer.add("")
        }
    }

    LazyColumn(
        modifier = modifier
    ) {
        items(item.size) { index ->
            val selectedAnswer = remember { mutableStateOf(answer[index]) }
            QuestionItem(
                question = item[index],
                modifier = modifier.padding(bottom = 8.dp),
                onAnswerSelected = { newAnswer ->
                    selectedAnswer.value = newAnswer
                    answer[index] = newAnswer
                },
                selectedAnswer = selectedAnswer.value
            )
        }
        item {
            Spacer(modifier = modifier.padding(8.dp))
            MainButton(
                modifier = modifier
                    .padding(bottom = 8.dp),
                onClick = {onSubmit()},
                labelText = "Selesai"
            )
        }
    }
}