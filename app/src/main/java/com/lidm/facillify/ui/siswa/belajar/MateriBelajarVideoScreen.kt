package com.lidm.facillify.ui.siswa.belajar

import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lidm.facillify.R
import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.data.local.VideoItem
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.SearchAppBar
import com.lidm.facillify.ui.responseStateScreen.ErrorScreen
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.ui.viewmodel.MateriBelajarViewModel
import com.lidm.facillify.util.ResponseState


@Composable
fun MateriBelajarVideoScreen(
    materiId: String,
    modifier: Modifier,
    onNavigateToVideoPlayer: (String, String) -> Unit,
) {

    val viewModel: MateriBelajarViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
    val materiDetailResponse by viewModel.materiDetailResponse.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMaterialDetail(materiId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        when (materiDetailResponse){
            is ResponseState.Loading -> {
                LoadingScreen()
            }
            is ResponseState.Success -> {
                ListMateriVideo(
                    modifier = modifier,
                    materi = (materiDetailResponse as ResponseState.Success<MateriBelajar?>).data as MateriBelajar,
                    onNavigateToVideoContent = onNavigateToVideoPlayer,
                )
            }
            is ResponseState.Error -> {
                ErrorScreen(
                    onTryAgain = { viewModel.getMaterialDetail(materiId) },
                )
            }
        }
    }
}

@Composable
fun ListMateriVideo(
    modifier: Modifier,
    materi: MateriBelajar,
    onNavigateToVideoContent: (String, String) -> Unit,
    isSearchBarVisible: Boolean = true,
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var active by rememberSaveable {
        mutableStateOf(false)
    }

    if (isSearchBarVisible) {
        SearchAppBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { active = false },
            active = false,
            onActiveChange = { active = false },
            label = "Cari video",
            modifier = modifier
        )
    }

    val filteredMateri = if (query != "") {
        materi.materiVideo.filter { it.title.contains(query, ignoreCase = true) }
    } else {
        materi.materiVideo
    }


    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(filteredMateri.size) {
            MateriVideoItem(
                modifier = modifier,
                onClick = { onNavigateToVideoContent(materi.id, filteredMateri[it].id) },
                videoItem = filteredMateri[it],
            )
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun MateriVideoItem(
    modifier: Modifier,
    onClick: () -> Unit,
    videoItem: VideoItem,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SecondaryBlue,
            contentColor = DarkBlue,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .height(100.dp)
                    .width(160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.TopCenter
            ) {
//                YouTubePlayer(videoId = videoItem.id, onFullScreenClick = {})
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(videoItem.thumbinal)
                        .crossfade(true)
                        .build(),
                    error = painterResource(id = R.drawable.connectivity5),
                    contentDescription = "video thumbinal",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(width = 160.dp, height = 120.dp),
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = modifier.padding(8.dp)
            ) {
                Text(
                    text = videoItem.title,
                    fontSize = 16.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = videoItem.desc,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 16.sp,
                    maxLines = 3,
                    modifier = modifier.padding(top = 8.dp),
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

