package com.lidm.facillify.ui.siswa.belajar

import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.View
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
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

    val DUMMY_VIDEO_URI =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    var isFullScreen by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri(Uri.parse(DUMMY_VIDEO_URI))
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
            }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    if (isFullScreen) {
        FullScreenVideoPlayer(
            exoPlayer = exoPlayer,
            onExitFullScreen = { isFullScreen = false }
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                AndroidView(
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = exoPlayer
                            useController = true
//                            setControllerVisibilityListener { visibility ->
//                                if (visibility == View.VISIBLE) {
//                                    // Add your logic here if needed
//                                }
//                            }
                            setFullscreenButtonClickListener {
                                isFullScreen = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(relatedContents.size) {
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
}

@Composable
fun FullScreenVideoPlayer(
    exoPlayer: ExoPlayer,
    onExitFullScreen: () -> Unit
) {
    val activity = LocalContext.current as? ComponentActivity

    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    Dialog(
        onDismissRequest = onExitFullScreen
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                        useController = true
//                        setControllerVisibilityListener { visibility ->
//                            if (visibility == View.VISIBLE) {
//
//                            }
//                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
