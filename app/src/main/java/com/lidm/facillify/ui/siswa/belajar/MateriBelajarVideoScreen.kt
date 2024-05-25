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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import com.lidm.facillify.ui.components.SearchAppBar
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue

data class MateriVideo(
    val id: Int,
    val title: String,
    val desc: String,
    val uri: String,
)

val dummyDataMateriVideo = listOf(
    MateriVideo(
        1,
        "Marvel Studios' Eternals | Official Trailer",
        "Marvel Studios’ “Eternals” welcomes an exciting new team of Super Heroes to the Marvel Cinematic Universe. The epic story, spanning thousands of years, features a group of immortal heroes forced out of the shadows to reunite against mankind’s oldest enemy, The Deviants.",
        "uNWKfPx1UWM"
    ),
    MateriVideo(
        2,
        "IRONMAN 4",
        "Teaser Trailer (2024) Robert Downey Jr. Returns as Tony Stark",
        "G0S6S9Sks70"
    ),
    MateriVideo(
        3,
        "Marvel Studios' Echo",
        "Marvel Studios' Echo | Official Trailer | Disney+ and Hulu",
        "AFUKnherhuw"
    ),
    MateriVideo(
        4,
        "ETERNALS 2: KING IN BLACK - First Trailer ",
        "Eternals star, Kumail Nanjiani, recently admitted that he wants a Kingo and Ms. Marvel team up in the Marvel Cinematic Universe. Directed by Academy Award-winning filmmaker, Chloé Zhao, the superhero ensemble film featured an all-star cast as immortal protectors of Earth. Joining Nanjiani, who portrayed the hero-turned-Bollywood star, Kingo, the film saw Gemma Chan as Sersi, Richard Madden as Ikaris, Lia McHugh as Sprite, Brian Tyree Henry as Phastos, Lauren Ridloff, as Makkari, Barry Keoghan as Druig, Don Lee as Gilgamesh, Salma Hayek as Ajak, and Angelina Jolie as Thena.",
        "rt2KZ4eRYDI"
    ),
)


@Composable
fun MateriBelajarVideoScreen(
    modifier: Modifier,
    onNavigateToVideoPlayer: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
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

        ListMateriVideo(
            modifier,
            dummyDataMateriVideo,
            onNavigateToVideoContent = onNavigateToVideoPlayer,
        )

    }
}

@Composable
fun ListMateriVideo(
    modifier: Modifier,
    videos: List<MateriVideo>,
    onNavigateToVideoContent: (Int) -> Unit,
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
            active = active,
            onActiveChange = { active = it },
            content = { /*TODO*/ },
            label = "Cari video",
            modifier = modifier
        )
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(videos.size) {
            MateriVideoItem(
                modifier = modifier,
                onClick = { onNavigateToVideoContent(videos[it].id) },
                videoItem = videos[it],
            )
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun MateriVideoItem(
    modifier: Modifier,
    onClick: () -> Unit,
    videoItem: MateriVideo,
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
                YouTubePlayer(videoId = "uNWKfPx1UWM", onFullScreenClick = {})
            }
//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_background),
//                contentDescription = "video",
//                modifier = Modifier
//                    .size(width = 160.dp, height = 120.dp),
//                contentScale = ContentScale.Crop
//            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = modifier.padding(8.dp)
            ) {
                Text(
                    text = videoItem.title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = videoItem.desc,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                    maxLines = 3,
                    modifier = modifier.padding(top = 8.dp),
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

