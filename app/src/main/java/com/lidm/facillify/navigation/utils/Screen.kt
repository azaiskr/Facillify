package com.lidm.facillify.navigation.utils

sealed class Screen (
    val route : String,
) {

    // === common route navigation === \\
    data object Konsultasi:  Screen("konsultasi")
    data object Chat: Screen ("chat")
    data object Belajar: Screen("belajar")
    data object MateriBelajar: Screen("materi_belajar")
    data object Latihan: Screen("latihan")
    data object Riwayat: Screen("riwayat")
    data object Profile: Screen("profile")

    data object TrackingList: Screen("tracking_list")
    data object TrackingDetail: Screen("tracking_detail")



    // === route navigation siswa === \\
    data object SiswaHome: Screen ("siswa_home")

    data object SiswaMateriBelajarDetail: Screen("materi_belajar/{materiId}"){
        fun createRoute(materiId: Int) = "materi_belajar/$materiId"
    }

    data object SiswaLatihanForm: Screen("latihan/{latihanId}"){
        fun createRoute(latihanId: Int) = "latihan/$latihanId"
    }
    data object SiswaMateriBelajarVideo: Screen("siswa_materi_belajar_video")

    data object SiswaVideoPlayer: Screen("siswa_materi_belajar_video/{videoId}"){
        fun createRoute(videoId: Int) = "siswa_materi_belajar_video/$videoId"
    }
    data object FormTambahDataOrtu: Screen("form_tambah_data_ortu")


    // === route navigation guru === \\
    data object TambahMateri: Screen("tabah_materi")
    data object TambahLatihan: Screen("tambah_latihan")
}