package com.lidm.facillify.navigation.utils

sealed class Screen (
    val route : String,
) {

    // === route navigation siswa === \\
    data object SiswaHome: Screen ("siswa_home")
    data object SiswaBelajar: Screen("siswa_belajar")
    data object SiswaKonsultasi:  Screen("siswa_konsultasi")
    data object SiswaRiwayat: Screen("siswa_riwayat")

}