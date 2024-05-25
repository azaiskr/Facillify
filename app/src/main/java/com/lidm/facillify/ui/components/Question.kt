package com.lidm.facillify.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.ui.theme.Orange
import com.lidm.facillify.util.Question

@Composable
fun QuestionItem(
    modifier: Modifier,
    question: Question,
    onAnswerSelected: (String) -> Unit,
    selectedAnswer: String,
//    isCorrect: Boolean,
){
    Column (
        modifier = modifier
            .padding(bottom = 16.dp)
    ){
        Text(
            text = question.questionText,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = modifier.padding(bottom = 4.dp)
        )
        question.answer.forEach { answer ->
            AnswerItem(
                answer = answer,
                onAnswerSelected = onAnswerSelected,
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
            .height(32.dp)
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
            style = TextStyle(
                fontSize = 14.sp,
            ),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionItemPreview(){
    QuestionItem(
        question = Question(
            id = 1,
            questionText = "Pada video ini kita akan mempelajari mengenai bangun ruang yang ada di sekitar kita",
            answer = listOf("Video 1", "Video 2", "Video 3")
        ),
        modifier = Modifier,
        onAnswerSelected = {},
        selectedAnswer = ""
    )
}