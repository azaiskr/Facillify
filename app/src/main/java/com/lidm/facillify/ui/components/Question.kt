package com.lidm.facillify.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.ui.theme.Orange
import com.lidm.facillify.data.local.Question
import com.lidm.facillify.data.remote.response.QuestionsItem

@Composable
fun QuestionItem(
    modifier: Modifier,
    question: QuestionsItem,
    onAnswerSelected: (Int, String) -> Unit,
    selectedAnswer: String,
){
    Column (
        modifier = modifier
            .padding(bottom = 16.dp)
    ){
        Text(
            text = question.question,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(bottom = 4.dp)
        )
        question.options?.forEachIndexed { index, answer ->
            AnswerItem(
                answer = answer,
                onAnswerSelected = {onAnswerSelected(index, answer)},
                modifier = modifier,
                selectedAnswer = selectedAnswer,
            )
        }
    }
}

@Composable
fun AnswerItem(
    answer: String,
    onAnswerSelected: (String) -> Unit,
    modifier: Modifier,
    selectedAnswer: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = (answer == selectedAnswer),
                onClick = { onAnswerSelected(answer) },
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (answer == selectedAnswer),
            colors = RadioButtonDefaults.colors(
                selectedColor = Orange,
                unselectedColor = Orange,
            ),
            onClick = null
        )
        Text(
            text = answer,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun QuestionItemPreview(){
//    QuestionItem(
//        question = Question(
//            id = 1,
//            questionText = "Pada video ini kita akan mempelajari mengenai bangun ruang yang ada di sekitar kita",
//            answer = listOf("Video 1", "Video 2", "Video 3")
//        ),
//        modifier = Modifier,
//        onAnswerSelected = { _, _ -> },
//        selectedAnswer = ""
//    )
//}