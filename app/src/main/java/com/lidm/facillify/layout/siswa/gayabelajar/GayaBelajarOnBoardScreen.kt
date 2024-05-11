package com.lidm.facillify.layout.siswa.gayabelajar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R

@Preview
@Composable
fun GayaBelajarOnBoardScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(PaddingValues(horizontal = 24.dp, vertical = 16.dp))
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Cek Gaya Belajarmu!",
            fontSize = 32.sp,
            modifier = modifier.width(160.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.mulaites),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp)
        )
        Column (
            horizontalAlignment = Alignment.Start,
        ){
            Text(
                text = "Sebelum melangkah lebih jauh",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Start
                ),
                modifier = modifier.fillMaxWidth()
            )
            Text(
                text = "Yuk kenali gaya belajar kamu dulu! Ikuti tes berikut ini yaa, pilih jawaban yang menggambarkan kamu banget.",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(400),
                    textAlign = TextAlign.Start,
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(vertical = 16.dp))
            )
            Text(
                text = "Semangat!",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(400),
                    textAlign = TextAlign.Start,
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(vertical = 16.dp))
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(
                onClick = {  },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                contentPadding = PaddingValues(vertical = 16.dp),

                ) {
                Text(
                    text = "Mulai Tes",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}