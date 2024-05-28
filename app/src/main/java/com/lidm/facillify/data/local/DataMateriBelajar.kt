package com.lidm.facillify.data.local

import android.app.Application
import com.lidm.facillify.R

data class MateriBelajar(
    val id: Int,
    val image: String,
    val title: String,
    val desc: String,
    val materiVideo: List<VideoItem>,
)

val materiBelajarData = listOf(
    MateriBelajar(
        id = 1,
        image = "https://asset-a.grid.id/crop/0x0:0x0/x/photo/2023/03/31/bangun-ruangjpg-20230331095251.jpg",
        title = "Bangun Ruang",
        desc = "Bab bangun ruang mencakup konsep-konsep dasar tentang berbagai bentuk tiga dimensi, seperti kubus, " +
                "balok, prisma, limas, tabung, kerucut, dan bola. Siswa akan mempelajari sifat-sifat dari masing-masing " +
                "bangun ruang, termasuk jumlah sisi, rusuk, dan titik sudut.",
        materiVideo = bangunRuangVideos,
    ),
)