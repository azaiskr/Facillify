package com.lidm.facillify.ui.siswa.gayabelajar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.components.MainButton
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.util.GayaBelajarResultPage

@Preview
@Composable
fun GayaBelajarResultScreen(
    modifier: Modifier = Modifier,
) {
    val result = GayaBelajarResultPage.Visual
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(PaddingValues(horizontal = 24.dp, vertical = 16.dp))
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Gaya Belajar Kamu ...",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = modifier.align(Alignment.CenterHorizontally)
            )

            TestResultScreen(modifier = modifier, resultPage = result)
            Spacer(modifier = Modifier.height(48.dp))
            MainButton(modifier = modifier, onClick = { /*TODO*/ }, labelText = "Lanjut")
        }
    }
}

@Composable
fun TestResultScreen(
    modifier: Modifier = Modifier,
    resultPage: GayaBelajarResultPage,
) {
    Image(
        painter = painterResource(id = resultPage.img),
        contentDescription = null,
        alignment = Alignment.Center,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
            .size(240.dp)
            .padding(8.dp, 0.dp)
    )
    Text(
        text = resultPage.title,
        modifier = modifier.fillMaxWidth(),
        style = TextStyle(
            fontSize = 24.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight(700),
            textAlign = TextAlign.Center,
        ),
    )
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.height(240.dp)
    ) {
        Text(
            text = resultPage.desc,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Justify,
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(PaddingValues(vertical = 16.dp))
        )
        Text(
            text = "Cara Belajar",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Start,
            ),
            color = DarkBlue,
            modifier = modifier
                .fillMaxWidth()
        )
        Text(
            text = resultPage.tips,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Justify,
            ),
            modifier = modifier
                .fillMaxWidth()
        )
    }
}