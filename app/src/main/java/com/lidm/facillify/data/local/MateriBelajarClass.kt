package com.lidm.facillify.data.local

import com.lidm.facillify.data.local.paketMateri.materi_bangun_ruang

data class MateriBelajar(
    val id: Int,
    val image: String,
    val title: String,
    val desc: String,
    val materiVideo: List<VideoItem>,
)

data class VideoItem(
    val id: String,
    val title: String,
    val thumbinal: String,
    val desc: String,
)

val listMateri = listOf(
    materi_bangun_ruang,

)

