package com.lidm.facillify.ui.siswa.belajar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.ui.siswa.FormSoal
import com.lidm.facillify.ui.theme.DarkBlue

@Composable
fun LatihanScreen(
    modifier: Modifier,
    latihanId: Int,
) {
    val latihan = dummyDataLatihan.find { it.id == latihanId } !!
    val answer = remember { mutableStateListOf<String>() }

    fun onSubmit () {Log.d("answer", "LatihanScreen: ${answer.toList()}")}

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

    FormLatihan(
        latihanItem = latihan,
        answer = answer,
        onSubmit = ::onSubmit
    )
}

@Composable
fun FormLatihan(
    latihanItem: LatihanItem,
    modifier: Modifier = Modifier,
    answer: MutableList<String>,
    onSubmit: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = latihanItem.judul,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = DarkBlue
            ),
            modifier = modifier
                .padding(top = 24.dp, bottom = 8.dp)
        )
        FormSoal(
            modifier = modifier,
            item = latihanItem.questions,
            answer = answer,
            onSubmit = onSubmit
        )
    }
}
