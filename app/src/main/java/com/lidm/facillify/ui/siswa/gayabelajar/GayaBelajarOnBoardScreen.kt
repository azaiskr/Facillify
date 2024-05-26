package com.lidm.facillify.ui.siswa.gayabelajar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.components.MainButton
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue

@Preview
@Composable
fun GayaBelajarOnBoardScreen(
    modifier: Modifier = Modifier,
    onNavigateToTestForm: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(PaddingValues(horizontal = 24.dp,))
            .padding(top=16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        TopSectionGayaBelajar(modifier = modifier)
        Spacer(modifier = modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.mulaites),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp)
        )
        Spacer(modifier = modifier.height(8.dp))
        Column (
            horizontalAlignment = Alignment.Start,
        ){
            Text(
                text = "Sebelum melangkah lebih jauh",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = modifier.fillMaxWidth(),
                color = DarkBlue
            )
            Text(
                text = "Yuk kenali gaya belajar kamu dulu! Ikuti tes berikut ini yaa, pilih jawaban yang menggambarkan kamu banget.",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Start,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(vertical = 16.dp))
            )
            Text(
                text = "Semangat!",
                fontSize = 14.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Start,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(vertical = 16.dp))
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        MainButton(modifier = modifier, onClick = { onNavigateToTestForm() }, labelText = "Mulai Tes")
    }
}

@Composable
fun TopSectionGayaBelajar(
    modifier: Modifier = Modifier,
){
    Box (
        modifier = modifier
            .fillMaxWidth()
    ){
        Canvas(modifier = Modifier
            .size(120.dp)
            .offset { IntOffset(100, -500.dp.roundToPx()) }
        ) {
            drawCircle(color = Blue, radius = 550.dp.toPx())
        }
        Text(
            text = "Cek Gaya\nBelajarmu!",
            fontSize = 32.sp,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
        )
    }
}

