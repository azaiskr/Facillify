package com.lidm.facillify.navigation.utils

sealed class Screen (
    val route : String,
) {
    // === auth route navigation === \
    data object OnBoarding: Screen("on_boarding")
    data object Login: Screen("login")
    data object Register: Screen("register")
    data object RegisterAs: Screen("register/{role}") {
        fun createRoute(role: String) = "register/$role"
    }


    // === common route navigation === \\
    data object Konsultasi:  Screen("konsultasi/{threadId}"){
        fun createRoute(threadId: String) = "konsultasi/$threadId"
    }
    data object Chat: Screen ("chat")
    data object Chatbot: Screen ("chatbot")
    data object Belajar: Screen("belajar")
    data object MateriBelajar: Screen("materi_belajar")
    data object Latihan: Screen("latihan")
    data object Riwayat: Screen("riwayat")
    data object Profile: Screen("profile")

    data object TrackingList: Screen("tracking_list")
    data object TrackingDetail: Screen("tracking_detail")



    // === route navigation siswa === \\
    data object GayaBelajarInterface: Screen("gaya_belajar")
    data object GayaBelajarTest: Screen("gaya_belajar_test")
    data object GayaBelajarTestResult: Screen("gaya_belajar_result")

    data object SiswaHome: Screen ("siswa_home")

    data object SiswaMateriBelajarDetail: Screen("materi_belajar/{materiId}"){
        fun createRoute(materiId: Int) = "materi_belajar/$materiId"
    }

    data object SiswaMateriBelajarVideo: Screen("materi_belajar/{materiId}/video") {
        fun createRoute(materiId: Int) = "materi_belajar/$materiId/video"
    }

    data object SiswaVideoPlayer: Screen("materi_belajar/{materiId}/video/{videoId}"){
        fun createRoute(materiId: Int, videoId: String) = "materi_belajar/$materiId/video/$videoId"
    }

    data object SiswaLatihanForm: Screen("latihan/{latihanId}"){
        fun createRoute(latihanId: Int) = "latihan/$latihanId"
    }
    data object FormTambahDataOrtu: Screen("form_tambah_data_ortu")

    data object KonsultasiThread: Screen("konsultasi_thread/{threadId}"){
        fun createRoute(threadId: String) = "konsultasi_thread/$threadId"
    }


    // === route navigation guru === \\
    data object TambahMateri: Screen("tabah_materi")
    data object TambahLatihan: Screen("tambah_latihan")
}