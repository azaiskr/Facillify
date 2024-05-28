package com.lidm.facillify.ui.siswa.belajar

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.media3.common.util.UnstableApi
import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.data.local.VideoItem
import com.lidm.facillify.data.local.listMateri
import com.lidm.facillify.data.local.paketMateri.materi_bangun_ruang
import com.lidm.facillify.ui.theme.DarkBlue
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.lidm.facillify.ui.siswa.belajar.BackHandler as _BackHandler

@Preview
@Composable
fun VideoPlayerScreen(
    modifier: Modifier = Modifier,
    videoId: String = "uNWKfPx1UWM",
    materiId: Int = 1,
    onNavigateToVideoContent: (Int, String) -> Unit ={ _, _ ->}
) {
    val materiBelajar = listMateri.find { it.id == materiId } !!
    val video = materiBelajar.materiVideo.find { it.id == videoId } !!

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
        videoContent = video,
        relatedContents = materiBelajar,
        onNavigateToVideoContent = onNavigateToVideoContent
    )

}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerContent(
    modifier: Modifier,
    videoContent: VideoItem,
    relatedContents: MateriBelajar,
    onNavigateToVideoContent: (Int, String) -> Unit
) {
    var isFullScreen by rememberSaveable {
        mutableStateOf(false)
    }

    if (isFullScreen) {
        FullScreenVideoPlayer(
            videoId = videoContent.id,
            onBackPressed = { isFullScreen = false }
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                YouTubePlayer(
                    videoId = videoContent.id,
                    onFullScreenClick = { isFullScreen = true })
            }
            Text(
                text = videoContent.title,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = DarkBlue,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(top = 24.dp, bottom = 8.dp)
                    .padding(horizontal = 16.dp)
            )
            Text(
                text = videoContent.desc,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                color = DarkBlue,
                textAlign = TextAlign.Justify,
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            )

            ListMateriVideo(
                modifier = modifier,
                materi = relatedContents,
                onNavigateToVideoContent = onNavigateToVideoContent,
                isSearchBarVisible = false
            )
        }
    }
}

@SuppressLint("ClickableViewAccessibility")
@Composable
fun YouTubePlayer(
    videoId: String,
    onFullScreenClick: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = { ctx ->
            YouTubePlayerView(ctx)

                .apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId, 0f)
                        youTubePlayer.play()
                    }
                })

                addFullScreenListener(object : YouTubePlayerFullScreenListener {
                    override fun onYouTubePlayerEnterFullScreen() {
                        onFullScreenClick()
                    }

                    override fun onYouTubePlayerExitFullScreen() {
                        onFullScreenClick()
                    }

                })

                setOnTouchListener { _, _ ->
                    onFullScreenClick()
                    true
                }
            }
        },

        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun BackHandler(onBackPressed: () -> Unit) {
    val currentOnBackPressed by rememberUpdatedState(onBackPressed)
    val activity = LocalContext.current as ComponentActivity

    DisposableEffect(Unit) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
        activity.onBackPressedDispatcher.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }
}

@Composable
fun FullScreenVideoPlayer(
    videoId: String,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val activity = context as? ComponentActivity

    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Dialog(
        onDismissRequest = onBackPressed
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            AndroidView(
                factory = { ctx ->
                    YouTubePlayerView(ctx).apply {
                        lifecycleOwner.lifecycle.addObserver(this)

                        addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(videoId, 0f)
                            }
                        })
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
            _BackHandler {
                onBackPressed()
            }
        }
    }
}
