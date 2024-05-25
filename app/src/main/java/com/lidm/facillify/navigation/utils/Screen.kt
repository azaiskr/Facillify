package com.lidm.facillify.navigation.utils

sealed class Screen (
    val route : String,
) {

    // === route navigation all role === \\
    data object Konsultasi:  Screen("konsultasi")
    data object Chat: Screen ("chat")


    // === route navigation siswa === \\
    data object SiswaHome: Screen ("siswa_home")
    data object SiswaBelajar: Screen("siswa_belajar")
    data object SiswaMateriBelajar: Screen("siswa_materi_belajar")
    data object SiswaMateriBelajarDetail: Screen("siswa_materi_belajar/{materiId}"){
        fun createRoute(materiId: Int) = "siswa_materi_belajar/$materiId"
    }
    data object SiswaLatihan: Screen("siswa_latihan")
    data object SiswaLatihanForm: Screen("siswa_latihan/{latihanId}"){
        fun createRoute(latihanId: Int) = "siswa_latihan/$latihanId"
    }
    data object SiswaMateriBelajarVideo: Screen("siswa_materi_belajar_video")
    data object SiswaVideoPlayer: Screen("siswa_materi_belajar_video/{videoId}"){
        fun createRoute(videoId: Int) = "siswa_materi_belajar_video/$videoId"
    }
    data object SiswaRiwayat: Screen("siswa_riwayat")
    data object SiswaProfile: Screen("siswa_profile")
    data object FormTambahDataOrtu: Screen("form_tambah_data_ortu")


}