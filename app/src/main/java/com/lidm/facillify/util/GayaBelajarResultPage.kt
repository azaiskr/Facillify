package com.lidm.facillify.util

import com.lidm.facillify.R

sealed class GayaBelajarResultPage (
    val img: Int,
    val title: String,
    val desc: String,
    val tips: String,
) {
    data object Kinestetik: GayaBelajarResultPage (
        img = R.drawable.kinestetik,
        title = "KINESTETIK",
        desc = "Gaya belajar kinestetik melibatkan penggunaan gerakan fisik dan pengalaman langsung dalam proses belajar",
        tips = "Gunakan permainan peran, simulasi, atau proyek-proyek praktis untuk belajar. Buat manipulatif atau model fisik untuk memahami konsep-konsep abstrak",
    )

    data object Auditorial: GayaBelajarResultPage (
        img = R.drawable.auditorial,
        title = "AUDITORIAL",
        desc = "Gaya belajar auditori adalah cara belajar di mana seseorang lebih mengandalkan indera pendengarannya untuk menyerap informasi.",
        tips = "Gunakan pendengaran, seperti mendengarkan penjelasan lisan, berbicara, dan berdiskusi untuk memahami materi.",
    )

    data object Visual : GayaBelajarResultPage(
        img = R.drawable.visual,
        title = "VISUAL",
        desc = "Gaya belajar visual adalah proses pembelajaran yang memfokuskan pada aspek visual sebagai penerima informasi dan pengetahuan utama.",
        tips = "Maksimalkan peran indra penglihatan dengan memanfaatkan gambar, grafik, dan visualisasi untuk memahami dan mengingat informasi.",
    )
}