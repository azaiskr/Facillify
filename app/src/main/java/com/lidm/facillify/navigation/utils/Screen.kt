package com.lidm.facillify.navigation.utils

sealed class Screen (
    val route : String,
) {

    // === route navigation siswa === \\
    data object SiswaHome: Screen ("siswa_home")
    data object SiswaBelajar: Screen("siswa_belajar")
    data object SiswaMateriBelajar: Screen("siswa_materi_belajar")
    data object SiswaMateriBelajarDetail: Screen("siswa_materi_belajar_detail")
    data object SiswaMateriBelajarVideo: Screen("siswa_materi_belajar_video")
    data object SiswaKonsultasi:  Screen("siswa_konsultasi")
    data object SiswaRiwayat: Screen("siswa_riwayat")
    data object SiswaProfile: Screen("siswa_profile")


}