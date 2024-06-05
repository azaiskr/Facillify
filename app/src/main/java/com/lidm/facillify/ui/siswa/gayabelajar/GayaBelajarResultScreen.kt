package com.lidm.facillify.ui.siswa.gayabelajar

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.MainButton
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.viewmodel.ProfileViewModel
import com.lidm.facillify.util.GayaBelajarResultPage
import com.lidm.facillify.util.ResponseState

@Preview
@Composable
fun GayaBelajarResultScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    context: Context = LocalContext.current
) {
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )

    val profileResponse = profileViewModel.profileResponse.collectAsState()
    val preferences by profileViewModel.getSession().observeAsState()

    LaunchedEffect(preferences) {
        preferences?.let {
            profileViewModel.getUserProfile(it.email)
        }
    }

    //val result = GayaBelajarResultPage.Visual
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (profileResponse.value) {
            is ResponseState.Success -> {
                Canvas(modifier = Modifier
//            .size(120.dp)
                    .fillMaxHeight(fraction = 0.15f)
                    .offset { IntOffset(500, -190.dp.roundToPx()) }
                ) {
                    drawCircle(color = Blue, radius = 550.dp.toPx())
                }
                Column(
                    modifier = modifier
                        .padding(PaddingValues(horizontal = 24.dp,))
                        .padding(top = 16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Gaya Belajar Kamu ...",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )

                    val learningStyle = (profileResponse.value as ResponseState.Success).data.result.learning_style.toString()

                    when (learningStyle) {
                        "Visual" -> {
                            TestResultScreen(modifier = modifier, resultPage = GayaBelajarResultPage.Visual)
                        }
                        "Auditori" -> {
                            TestResultScreen(modifier = modifier, resultPage = GayaBelajarResultPage.Auditorial)
                        }
                        "Kinestetik" -> {
                            TestResultScreen(modifier = modifier, resultPage = GayaBelajarResultPage.Kinestetik)
                        }
                        "Audio-Kinestetik" -> {
                            TestResultScreen(modifier = modifier, resultPage = GayaBelajarResultPage.Auditorial)
                        }
                        "Audio-Visual" -> {
                            TestResultScreen(modifier = modifier, resultPage = GayaBelajarResultPage.Auditorial)
                        }
                        "Kinestetik-Visual" -> {
                            TestResultScreen(modifier = modifier, resultPage = GayaBelajarResultPage.Kinestetik)
                        }
                        else -> {
                            TestResultScreen(modifier = modifier, resultPage = GayaBelajarResultPage.Visual)
                        }
                    }

                    //TestResultScreen(modifier = modifier, resultPage = "")
                    Spacer(modifier = Modifier.height(24.dp))
                    MainButton(
                        modifier = modifier,
                        onClick = { onNavigateToHome() },
                        labelText = "Lanjut"
                    )
                }
            }

            is ResponseState.Error -> {

            }

            is ResponseState.Loading -> {
                LoadingScreen()
            }
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
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = Color.White
    )
    Spacer(modifier = modifier.height(8.dp))
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxHeight(fraction = 0.4f)
    ) {
        Text(
            text = resultPage.desc,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight(400),
            textAlign = TextAlign.Justify,
            modifier = modifier
                .fillMaxWidth()
                .padding(PaddingValues(vertical = 16.dp))
        )
        Text(
            text = "Cara Belajar",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = DarkBlue,
            modifier = modifier
                .fillMaxWidth()
        )
        Text(
            text = resultPage.tips,
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            modifier = modifier
                .fillMaxWidth()
        )
    }
}