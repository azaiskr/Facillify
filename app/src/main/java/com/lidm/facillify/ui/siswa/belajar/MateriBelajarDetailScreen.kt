package com.lidm.facillify.ui.siswa.belajar

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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lidm.facillify.R
import com.lidm.facillify.data.local.listMateri
import com.lidm.facillify.data.local.paketMateri.materi_bangun_ruang
import com.lidm.facillify.ui.theme.DarkBlue

@Composable
fun MateriBelajarDetailScreen(
    modifier: Modifier,
    materiId: Int,
    onNavigateToMateriVideo: (Int) -> Unit,
    onNavigateToMateriMusik: (Int) -> Unit,
) {

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
    DetailMateriBelajar(
        modifier = modifier,
        onNavigateToMateriVideo = {onNavigateToMateriVideo(materiId)},
        materiId = materiId,
        onNavigateToMateriMusik = {onNavigateToMateriMusik(materiId)}
    )
}

@Composable
fun DetailMateriBelajar(
    modifier: Modifier,
    onNavigateToMateriVideo: (Int) -> Unit,
    onNavigateToMateriMusik: (Int) -> Unit,
    materiId: Int,
) {
    val data = listMateri.find { it.id == materiId }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        if (data != null) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(data.image)
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
                text = data.title,
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
                text = data.desc,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = modifier.height(200.dp)
            )
        }
        BtnMateriBelajar(
            iconVector = R.drawable.videoimage,
            text = "Materi Video",
            modifier = modifier,
            onClick = { onNavigateToMateriVideo(materiId) }
        )
        Spacer(modifier = modifier.height(8.dp))
        BtnMateriBelajar(
            iconVector = R.drawable.musicimage,
            text = "Materi Musik",
            modifier = modifier,
            onClick = { onNavigateToMateriMusik(materiId) }
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

@Preview
@Composable
fun DetailMateriBelajarPreview(
    modifier: Modifier = Modifier
) {
    DetailMateriBelajar(modifier = modifier, onNavigateToMateriVideo = {}, materiId = 1, onNavigateToMateriMusik = {})
}
