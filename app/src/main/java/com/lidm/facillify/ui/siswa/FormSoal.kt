package com.lidm.facillify.ui.siswa

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lidm.facillify.ui.components.MainButton
import com.lidm.facillify.ui.components.QuestionItem
import com.lidm.facillify.util.Question

@Composable
fun FormSoal(
    modifier : Modifier,
    item: List<Question>,
    onSubmit: () -> Unit,
    answer: MutableList<String>
){
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