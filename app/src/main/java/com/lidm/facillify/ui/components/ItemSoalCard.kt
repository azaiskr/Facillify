package com.lidm.facillify.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Orange
import com.lidm.facillify.ui.theme.SecondaryBlue

@Composable
fun ItemSoalCard(
    soal: String,
    jawaban: List<String>,
    jawabanBenar: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = SecondaryBlue,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = soal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Black)
Spacer(modifier = Modifier.height(8.dp))
            for (jawaban in jawaban) {
                AnswerOption(answer = jawaban, isTrue = jawaban == jawabanBenar)
            }
            Spacer(modifier = Modifier.height(8.dp))
            EditAndDelete(onEditClick = { onEditClick() }, onDeleteClick = { onDeleteClick() })
        }

    }
}

@Composable
fun AnswerOption(
    answer: String,
    isTrue: Boolean
){
    Row(
        modifier = Modifier.fillMaxWidth().height(36.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(selected = isTrue , onClick = { /*TODO*/ }, colors = RadioButtonDefaults.colors(
            disabledSelectedColor = Orange,
            disabledUnselectedColor = Orange,
        ), enabled = false)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = answer, fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Black)
    }
}

@Composable
fun EditAndDelete(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = { onEditClick() }, colors = ButtonDefaults.buttonColors(
            containerColor = Orange
        ), shape = RoundedCornerShape(8.dp)
        ){
            Text(text = "Sunting", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = { onDeleteClick() }, colors = ButtonDefaults.buttonColors(
            containerColor = AlertRed
        ), shape = RoundedCornerShape(8.dp)
        ){
            Text(text = "Hapus", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ItemSoalCardPreview() {
    ItemSoalCard(
        soal = "Kenapa orang bernafas menggunakan paru paru...",
        jawaban = listOf("A", "B", "C", "D"),
        jawabanBenar = "A",
        onEditClick = { /*TODO*/ },
        onDeleteClick = { /*TODO*/ }
    )
}