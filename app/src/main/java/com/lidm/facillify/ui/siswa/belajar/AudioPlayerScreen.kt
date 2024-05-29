package com.lidm.facillify.ui.siswa.belajar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lidm.facillify.data.local.listMateri

@Composable
fun AudioPlayerScreen(
    modifier: Modifier,
    audioId: String,
    materiId: Int,
    onNavigateToAudioContent: (Int, String) -> Unit,
) {
    val materiBelajar = listMateri.find { it.id == materiId }!!
    val audio = materiBelajar.materiAudio.find { it.id == audioId }!!

    VideoPlayerContent(
        modifier = modifier,
        videoContent = audio,
        relatedContents = materiBelajar,
        onNavigateToVideoContent = onNavigateToAudioContent,
        contentVideo = false,
    )
}