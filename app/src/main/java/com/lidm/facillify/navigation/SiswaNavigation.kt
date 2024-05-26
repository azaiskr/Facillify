package com.lidm.facillify.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lidm.facillify.navigation.utils.Screen
import com.lidm.facillify.ui.chat.ChatScreen
import com.lidm.facillify.ui.chat.ChatbotScreen
import com.lidm.facillify.ui.components.MainBottomAppBar
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.siswa.SiswaHomeScreen
import com.lidm.facillify.ui.siswa.SiswaRiwayatScreen
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarDetailScreen
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarScreen
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarVideoScreen
import com.lidm.facillify.ui.siswa.belajar.BelajarScreen
import com.lidm.facillify.ui.chat.konsultasi.ListKonsultasi
import com.lidm.facillify.ui.siswa.FormEditEmailOrtu
import com.lidm.facillify.ui.profile.ProfileScreen
import com.lidm.facillify.ui.siswa.belajar.LatihanScreen
import com.lidm.facillify.ui.siswa.belajar.LatihanSiswaListScreen
import com.lidm.facillify.ui.siswa.belajar.VideoPlayerScreen
import com.lidm.facillify.ui.chat.konsultasi.ChatKonsultasi
import com.lidm.facillify.ui.siswa.gayabelajar.GayaBelajarOnBoardScreen
import com.lidm.facillify.ui.siswa.gayabelajar.GayaBelajarResultScreen
import com.lidm.facillify.ui.siswa.gayabelajar.GayaBelajarTest
import com.lidm.facillify.util.Role

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SiswaNavigation(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val role = Role.STUDENT
    val name = "Student"

    Scaffold(
        bottomBar = {
            MainBottomAppBar(
                navController = navController,
                role = role,
                modifier = modifier,
                hideBottomBar = !(currentRoute == Screen.SiswaHome.route ||
                        currentRoute == Screen.Belajar.route ||
                        currentRoute == Screen.Konsultasi.route ||
                        currentRoute == Screen.Riwayat.route)
            )
        },
        topBar = {
            MainTopAppBar(
                sectionTitle = when (currentRoute) {
                    Screen.SiswaHome.route -> "Hallo, $name"
                    Screen.Belajar.route -> "Belajar"
                    Screen.MateriBelajar.route -> "Materi Belajar"
                    Screen.SiswaMateriBelajarDetail.route -> "Detail Materi"
                    Screen.SiswaMateriBelajarVideo.route -> "Materi Video"
                    Screen.Latihan.route -> "Latihan Soal"
                    Screen.Konsultasi.route -> "Konsultasi"
                    Screen.Chatbot.route -> "FACILLIFY AI"
                    Screen.Riwayat.route -> "Riwayat Kamu"
                    Screen.Profile.route -> "Profil Siswa"
                    Screen.FormTambahDataOrtu.route -> "Tambah Data Orang Tua"
                    else -> ""
                },
                backIcon = when (currentRoute) {
                    Screen.SiswaHome.route -> false
                    Screen.Belajar.route -> false
                    Screen.Konsultasi.route -> false
                    Screen.Riwayat.route -> false
                    else -> true
                },
                onBackClick = { navController.popBackStack() },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                profileIcon = when (currentRoute) {
                    Screen.Profile.route -> false
                    Screen.FormTambahDataOrtu.route -> false
                    else -> true
                },
                isHide = currentRoute == Screen.SiswaLatihanForm.route || currentRoute == Screen.Chat.route,
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SiswaHome.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.SiswaHome.route) {
                SiswaHomeScreen(
                    modifier = modifier,
                    onNavigateToBelajar = {navController.navigate(Screen.MateriBelajar.route)},
                    onNavigateToLatihan = {navController.navigate(Screen.Latihan.route)},
                    onItemBelajarClick = {materiId -> navController.navigate(Screen.SiswaMateriBelajarDetail.createRoute(materiId))},
                    onItemLatihanClick = {latihanId -> navController.navigate(Screen.SiswaLatihanForm.createRoute(latihanId))},
                    onNavigateToChatbot = {navController.navigate(Screen.Chatbot.route)}

                )
            }


            // BELAJAR
            composable(Screen.Belajar.route) {
                BelajarScreen(
                    modifier = modifier,
                    onBelajarClick = { navController.navigate(Screen.MateriBelajar.route) },
                    onLatihanClick = {navController.navigate(Screen.Latihan.route)}
                )
            }
            // BELAJAR-MATERI
            composable(Screen.MateriBelajar.route) {
                MateriBelajarScreen(
                    modifier = modifier,
                    onNavigateToMateriBelajarDetail = { materiId ->
                        navController.navigate(Screen.SiswaMateriBelajarDetail.createRoute(materiId))
                    }
                )
            }
            composable(
                route = Screen.SiswaMateriBelajarDetail.route,
                arguments = listOf(navArgument("materiId") { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt("materiId") ?: 0
                MateriBelajarDetailScreen(
                    modifier = modifier,
                    materiId = id,
                    onNavigateToMateriVideo = { navController.navigate(Screen.SiswaMateriBelajarVideo.route) }
                )
            }
            composable(Screen.SiswaMateriBelajarVideo.route) {
                MateriBelajarVideoScreen(
                    modifier = modifier,
                    onNavigateToVideoPlayer = { videoId ->
                        navController.navigate(Screen.SiswaVideoPlayer.createRoute(videoId))
                    }
                )
            }
            composable(
                route = Screen.SiswaVideoPlayer.route,
                arguments = listOf(navArgument("videoId") { type = NavType.IntType })
            ){
                    val id = it.arguments?.getInt("videoId") ?: 0
                    VideoPlayerScreen(
                        modifier = modifier,
                        id = id
                    )
            }
            //BELAJAR - LATIHAN
            composable(Screen.Latihan.route){
                LatihanSiswaListScreen(
                    onNavigateToLatihanForm = {latihanId ->
                        navController.navigate(Screen.SiswaLatihanForm.createRoute(latihanId))
                    },
                    modifier = modifier,
                )
            }
            composable(
                route = Screen.SiswaLatihanForm.route,
                arguments = listOf(navArgument("latihanId"){type = NavType.IntType})
            ) {
                val id = it.arguments?.getInt("latihanId") ?: 0
                LatihanScreen(
                    modifier = modifier,
                    latihanId = id
                )
            }

            composable(Screen.Konsultasi.route) {
                ListKonsultasi(
                    modifier = modifier,
                    onNavigateToChat = { navController.navigate(Screen.Chat.route) }
                )
            }

            composable(Screen.Chat.route){
                ChatKonsultasi(
                    onBackClick = {navController.popBackStack()}
                )
            }

            composable(Screen.Chatbot.route){
                ChatbotScreen()
            }

            composable(Screen.Riwayat.route) {
                SiswaRiwayatScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    modifier = modifier,
                    navigateToFormTambahDataOrtu = { navController.navigate(Screen.FormTambahDataOrtu.route) }
                )
            }
            composable(Screen.FormTambahDataOrtu.route) {
                FormEditEmailOrtu(
                    onClick = {navController.popBackStack()}
                )
            }
        }
    }
}