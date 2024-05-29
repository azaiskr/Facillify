package com.lidm.facillify.ui.siswa

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lidm.facillify.ui.components.MainButton
import com.lidm.facillify.ui.components.QuestionItem
import com.lidm.facillify.data.local.Question

@Composable
fun FormSoal(
    modifier : Modifier,
    item: List<Question>,
    onSubmit: () -> Unit,
    answer: MutableList<String>,
    totalTimeMinutes: Int = 60,
){
    val remainingTime = remember { mutableIntStateOf(totalTimeMinutes * 60) }
    val minutes = remainingTime.intValue / 60
    val seconds = remainingTime.intValue % 60
    if (answer.isEmpty()) {
        repeat(item.size) {
            answer.add("")
        }
    }

    fun mapIndexToAnswer(index: Int): String {
        return ('A' + index).toString()
    }

    LazyColumn(
        modifier = modifier
    ) {
        items(item.size) { index ->
            val selectedAnswer = rememberSaveable { mutableStateOf(answer[index]) }
            QuestionItem(
                question = item[index],
                modifier = modifier.padding(bottom = 8.dp),
                onAnswerSelected = { newAnswerIndex, newAnswerValue ->
                    val newAnswer = mapIndexToAnswer(newAnswerIndex)
                    selectedAnswer.value = newAnswerValue
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