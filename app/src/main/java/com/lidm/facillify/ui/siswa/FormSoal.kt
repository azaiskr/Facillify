package com.lidm.facillify.ui.siswa

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lidm.facillify.data.remote.response.QuestionsItem
import com.lidm.facillify.ui.components.MainButton
import com.lidm.facillify.ui.components.QuestionItem

@Composable
fun FormSoal(
    modifier: Modifier,
    item: List<QuestionsItem>,
    onSubmit: () -> Unit,
    answer: MutableList<String>,
) {
    if (answer.isEmpty()) {
        repeat(item.size) {
            answer.add("")
        }
    }

    fun mapIndexToAnswer(index: Int): String {
        return ('a' + index).toString()
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
                onClick = { onSubmit() },
                labelText = "Selesai"
            )
        }
    }
}