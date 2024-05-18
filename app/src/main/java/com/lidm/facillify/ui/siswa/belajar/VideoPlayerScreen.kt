package com.lidm.facillify.ui.siswa.belajar

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.theme.DarkBlue

@Preview
@Composable
fun VideoPlayerScreen(
    modifier: Modifier = Modifier,
) {
    val title = "Video 1"
    val desc = "Pada video ini kita akan mempelajari mengenai bangun ruang yang ada di sekitar kita"
    val relatedContents = listOf("Video 2", "Video 3", "Video 4")

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
    VideoPlayerContent(
        modifier = modifier,
        playedTitle = title,
        playedDesc = desc,
        relatedContents = relatedContents
    )

}

@Composable
fun VideoPlayerContent(
    modifier: Modifier,
    playedTitle: String,
    playedDesc: String,
    relatedContents: List<String>
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //TODO: ExoPlayer
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "video",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = playedTitle,
            style = TextStyle(
                fontSize = 24.sp,
                color = DarkBlue,
                fontWeight = FontWeight.Bold,
            ),
            modifier = modifier.padding(top = 24.dp, bottom = 8.dp)
        )
        Text(
            text = playedDesc,
            style = TextStyle(
                fontSize = 16.sp,
                color = DarkBlue,
            ),
        )
        Spacer(modifier = modifier.height(32.dp))
        Text(
            text = "Video Terkait",
            style = TextStyle(
                fontSize = 18.sp,
                color = DarkBlue,
                fontWeight = FontWeight.Medium
            ),
            modifier = modifier.padding(vertical = 16.dp)
        )
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(relatedContents.size){
                MateriVideoItem(
                    modifier = modifier,
                    onClick = { /*TODO*/ },
                    titleVideo = relatedContents[it],
                    descVideo = relatedContents[it]
                )
            }
        }

    }
}
