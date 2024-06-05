package com.lidm.facillify.ui.siswa.belajar

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lidm.facillify.R
import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.data.local.listMateri
import com.lidm.facillify.data.local.paketMateri.materi_bangun_ruang
import com.lidm.facillify.data.remote.response.MaterialList
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.responseStateScreen.ErrorScreen
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.viewmodel.MateriBelajarViewModel
import com.lidm.facillify.util.ResponseState

@Composable
fun MateriBelajarDetailScreen(
    modifier: Modifier,
    materiId: String,
    onNavigateToMateriVideo: (String) -> Unit,
    onNavigateToMateriMusik: (String) -> Unit,
) {
    val viewModel: MateriBelajarViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
    val materiDetailResponse by viewModel.materiDetailResponse.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMaterialDetail(materiId)
        Log.d("MateriBelajarDetailScreen", "MateriBelajarDetailScreen: $materiId")
    }

    when (materiDetailResponse) {
        is ResponseState.Loading -> {
            LoadingScreen()
        }

        is ResponseState.Success -> {
            DetailMateriBelajar(
                modifier = modifier,
                onNavigateToMateriVideo = { onNavigateToMateriVideo(materiId) },
                materi = (materiDetailResponse as ResponseState.Success<MateriBelajar?>).data as MateriBelajar,
                onNavigateToMateriMusik = { onNavigateToMateriMusik(materiId) }
            )
        }

        is ResponseState.Error -> {
            ErrorScreen(
                onTryAgain = { viewModel.getMaterialDetail(materiId) }
            )
        }
    }
}

@Composable
fun DetailMateriBelajar(
    modifier: Modifier,
    onNavigateToMateriVideo: (String) -> Unit,
    onNavigateToMateriMusik: (String) -> Unit,
    materi: MateriBelajar,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(materi.image)
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.connectivity5),
            contentDescription = "Materi Poster",
            contentScale = ContentScale.Fit,
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
            modifier = modifier.height(200.dp)
        )
        BtnMateriBelajar(
            iconVector = R.drawable.videoimage,
            text = "Materi Video",
            modifier = modifier,
            onClick = { onNavigateToMateriVideo(materi.id) }
        )
        Spacer(modifier = modifier.height(8.dp))
        BtnMateriBelajar(
            iconVector = R.drawable.musicimage,
            text = "Materi Musik",
            modifier = modifier,
            onClick = { onNavigateToMateriMusik(materi.id) }
        )
    }
}

@Composable
fun BtnMateriBelajar(
    iconVector: Int,
    text: String,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = DarkBlue,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = iconVector),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .clip(CircleShape)
                    .size(40.dp)
            )
            Spacer(modifier = modifier.width(16.dp))
            Text(
                text = text,
                modifier = modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium,
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null,
                modifier = modifier
                    .weight(0.1f)
            )

        }
    }
}
