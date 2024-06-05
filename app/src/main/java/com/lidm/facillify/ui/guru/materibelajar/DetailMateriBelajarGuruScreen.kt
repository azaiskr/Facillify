package com.lidm.facillify.ui.guru.materibelajar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lidm.facillify.R
import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.data.local.listMateri
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.responseStateScreen.ErrorScreen
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.siswa.belajar.MateriVideoItem
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.viewmodel.MateriBelajarViewModel
import com.lidm.facillify.util.ResponseState

@Composable
fun DetailMateriBelajarGuruScreen(
    materiId: String,
    modifier: Modifier,
) {
    val viewModel: MateriBelajarViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
    val materiBelajar by viewModel.materiDetailResponse.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getMaterialDetail(materiId)
    }
    when(materiBelajar) {
        is ResponseState.Loading ->  LoadingScreen()
        is ResponseState.Success -> {
            val materi = (materiBelajar as ResponseState.Success).data ?: return
            DetailMateriGuru(
                materi = materi,
                modifier = modifier,
            )
        }
        is ResponseState.Error -> {
            ErrorScreen(
                onTryAgain = {viewModel.getMaterialDetail(materiId)}
            )
        }
    }


}

@Composable
fun DetailMateriGuru(
    materi: MateriBelajar,
    modifier: Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(materi.image)
                        .crossfade(true)
                        .build(),
                    error = painterResource(id = R.drawable.connectivity5),
                    contentDescription = "Materi Poster",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(240.dp),
                )

                Text(
                    text = materi.title,
                    fontSize = 20.sp,
                    color = DarkBlue,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 8.dp)
                        .wrapContentHeight()
                )
                Text(
                    text = materi.desc,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )
            }
            Text(
                text = "Video Belajar",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Black,
                color = Blue,
                modifier = modifier.padding(top = 8.dp)
            )
        }
        items(materi.materiVideo.size) {
            MateriVideoItem(
                modifier = modifier,
                onClick = { },
                videoItem = materi.materiVideo[it]
            )
        }
        item {
            Text(
                text = "Audio Belajar",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Black,
                color = Blue,
                modifier = modifier.padding(top = 16.dp)
            )
        }
        items(materi.materiAudio.size){
            MateriVideoItem(
                modifier = modifier,
                onClick = { },
                videoItem = materi.materiAudio[it]
            )
        }
    }
}
