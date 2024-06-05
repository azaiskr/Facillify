package com.lidm.facillify.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lidm.facillify.navigation.utils.Screen
import com.lidm.facillify.ui.chat.ChatbotScreen
import com.lidm.facillify.ui.components.MainBottomAppBar
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.konsultasi.KonsultasiDetailScreen
import com.lidm.facillify.ui.konsultasi.KonsultasiForumScreen
import com.lidm.facillify.ui.profile.ProfileScreen
import com.lidm.facillify.ui.siswa.FormEditEmailOrtu
import com.lidm.facillify.ui.siswa.SiswaHomeScreen
import com.lidm.facillify.ui.siswa.belajar.AudioPlayerScreen
import com.lidm.facillify.ui.siswa.belajar.BelajarScreen
import com.lidm.facillify.ui.siswa.belajar.LatihanScreen
import com.lidm.facillify.ui.siswa.belajar.LatihanSiswaListScreen
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarAudioScreen
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarDetailScreen
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarScreen
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarVideoScreen
import com.lidm.facillify.ui.siswa.belajar.VideoPlayerScreen
import com.lidm.facillify.ui.siswa.gayabelajar.GayaBelajarOnBoardScreen
import com.lidm.facillify.ui.siswa.gayabelajar.GayaBelajarTest
import com.lidm.facillify.ui.siswa.riwayat.RiwayatScreen
import com.lidm.facillify.util.Role

@RequiresApi(Build.VERSION_CODES.O)
//@Preview
@Composable
fun SiswaNavigation(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val role = Role.STUDENT

    Scaffold(
        containerColor = Color.White,
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
                    //Screen.SiswaHome.route -> "Home"
                    Screen.Belajar.route -> "Belajar"
                    Screen.MateriBelajar.route -> "Materi Belajar"
                    Screen.SiswaMateriBelajarDetail.route -> "Detail Materi"
                    Screen.SiswaMateriBelajarVideo.route -> "Materi Video"
                    Screen.SiswaVideoPlayer.route -> "Video Player"
                    Screen.SiswaMateriBelajarAudio.route -> "Materi Audio"
                    Screen.SiswaAudioPlayer.route -> "Audio Player"
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
                    Screen.SiswaHome.route -> true
                    Screen.Belajar.route -> true
                    Screen.Konsultasi.route -> true
                    Screen.Riwayat.route -> true
                    else -> false
                },
                isHide = currentRoute == Screen.SiswaLatihanForm.route || currentRoute == Screen.Chat.route || currentRoute == Screen.GayaBelajarInterface.route || currentRoute == Screen.GayaBelajarTest.route || currentRoute == Screen.GayaBelajarTestResult.route,
                isHome = currentRoute == Screen.SiswaHome.route,
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SiswaHome.route,
            modifier = modifier.padding(innerPadding),
            enterTransition = {
                when (targetState.destination.route) {
                    Screen.SiswaHome.route, Screen.Belajar.route, Screen.Riwayat.route, Screen.Konsultasi.route -> slideInHorizontally(
                        initialOffsetX = { 1000 }) + fadeIn()

                    else -> scaleIn(initialScale = 0.8f) + fadeIn()
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.SiswaHome.route, Screen.Belajar.route, Screen.Riwayat.route, Screen.Konsultasi.route -> slideOutHorizontally(
                        targetOffsetX = { -1000 }) + fadeOut()

                    else -> fadeOut()
                }
            },
            popEnterTransition = {
                when (targetState.destination.route) {
                    Screen.SiswaHome.route -> if (initialState.destination.route == Screen.Belajar.route ||
                        initialState.destination.route == Screen.Riwayat.route ||
                        initialState.destination.route == Screen.Konsultasi.route
                    ) {
                        slideInHorizontally(initialOffsetX = { -1000 })
                    } else {
                        scaleIn(initialScale = 0.8f) + fadeIn()
                    }

                    else -> scaleIn(initialScale = 0.8f) + fadeIn()
                }
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            }
        ) {
            composable(Screen.SiswaHome.route) {
                SiswaHomeScreen(
                    modifier = modifier,
                    onNavigateToBelajar = { navController.navigate(Screen.MateriBelajar.route) },
                    onNavigateToLatihan = { navController.navigate(Screen.Latihan.route) },
                    onItemBelajarClick = { materiId ->
                        navController.navigate(
                            Screen.SiswaMateriBelajarDetail.createRoute(
                                materiId
                            )
                        )
                    },
                    onItemLatihanClick = { latihanId ->
                        navController.navigate(
                            Screen.SiswaLatihanForm.createRoute(
                                latihanId
                            )
                        )
                    },
                    onNavigateToChatbot = { navController.navigate(Screen.Chatbot.route) },
                    onNavigateToTestGayaBelajar = { navController.navigate(Screen.GayaBelajarInterface.route) }
                )
            }

            // GAYA BELAJAR INTERFACE
            composable(Screen.GayaBelajarInterface.route) {
                GayaBelajarOnBoardScreen(
                    onNavigateToTestForm = { navController.navigate(Screen.GayaBelajarTest.route) }
                )
            }

            //GAYA BELAJAR TEST
            composable(Screen.GayaBelajarTest.route) {
                GayaBelajarTest(
                    modifier = modifier,
                    onNavigateToTestResult = { navController.navigate(Screen.GayaBelajarTestResult.route) }
                )
            }

            composable(Screen.GayaBelajarTestResult.route) {
                GayaBelajarTest(
                    modifier = modifier,
                    onNavigateToTestResult = { navController.navigate(Screen.SiswaHome.route) }
                )
            }

            // BELAJAR
            composable(Screen.Belajar.route) {
                BelajarScreen(
                    modifier = modifier,
                    onBelajarClick = { navController.navigate(Screen.MateriBelajar.route) },
                    onLatihanClick = { navController.navigate(Screen.Latihan.route) }
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
                arguments = listOf(navArgument("materiId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("materiId") ?: ""
                MateriBelajarDetailScreen(
                    modifier = modifier,
                    materiId = id,
                    onNavigateToMateriVideo = { materiId ->
                        navController.navigate(Screen.SiswaMateriBelajarVideo.createRoute(materiId))
                    },
                    onNavigateToMateriMusik = { materiId ->
                        navController.navigate(Screen.SiswaMateriBelajarAudio.createRoute(materiId))
                    }
                )
            }
            composable(
                route = Screen.SiswaMateriBelajarVideo.route,
                arguments = listOf(navArgument("materiId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("materiId") ?: ""
                MateriBelajarVideoScreen(
                    modifier = modifier,
                    materiId = id,
                    onNavigateToVideoPlayer = { materiId, videoId ->
                        navController.navigate(
                            Screen.SiswaVideoPlayer.createRoute(
                                materiId,
                                videoId
                            )
                        )
                    }
                )
            }
            composable(
                route = Screen.SiswaVideoPlayer.route,
                arguments = listOf(
                    navArgument("materiId") { type = NavType.StringType },
                    navArgument("videoId") { type = NavType.StringType }
                )
            ) {
                val materiId = it.arguments?.getString("materiId") ?: ""
                val videoId = it.arguments?.getString("videoId") ?: ""
                VideoPlayerScreen(
                    modifier = modifier,
                    videoId = videoId,
                    materiId = materiId,
                    onNavigateToVideoContent = { materiId, videoId2 ->
                        navController.navigate(
                            Screen.SiswaVideoPlayer.createRoute(
                                materiId,
                                videoId2
                            )
                        )
                    }
                )
            }
            // BELAJAR - AUDIO
            composable(
                route = Screen.SiswaMateriBelajarAudio.route,
                arguments = listOf(navArgument("materiId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("materiId") ?: ""
                MateriBelajarAudioScreen(
                    modifier = modifier,
                    materiId = id,
                    onNavigateToAudioPlayer = { materiId, audioId ->
                        navController.navigate(
                            Screen.SiswaAudioPlayer.createRoute(
                                materiId,
                                audioId
                            )
                        )
                    }
                )
            }
            composable(
                route = Screen.SiswaAudioPlayer.route,
                arguments = listOf(
                    navArgument("materiId") { type = NavType.StringType },
                    navArgument("audioId") { type = NavType.StringType }
                )
            ) {
                val _materiId = it.arguments?.getString("materiId") ?: ""
                val _audioId = it.arguments?.getString("audioId") ?: ""
                AudioPlayerScreen(
                    modifier = modifier,
                    audioId = _audioId,
                    materiId = _materiId,
                    onNavigateToAudioContent = { materiId, audioId ->
                        navController.navigate(
                            Screen.SiswaAudioPlayer.createRoute(
                                materiId,
                                audioId
                            )
                        )
                    }
                )
            }


            //BELAJAR - LATIHAN
            composable(Screen.Latihan.route) {
                LatihanSiswaListScreen(
                    onNavigateToLatihanForm = { latihanId ->
                        navController.navigate(Screen.SiswaLatihanForm.createRoute(latihanId))
                    },
                    modifier = modifier,
                )
            }
            composable(
                route = Screen.SiswaLatihanForm.route,
                arguments = listOf(navArgument("latihanId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("latihanId") ?: ""
                LatihanScreen(
                    modifier = modifier,
                    latihanId = id,
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )
            }
            
            //CONSULTATION -> THREAD
            composable(Screen.Konsultasi.route) {
                KonsultasiForumScreen {
                    navController.navigate(Screen.KonsultasiThread.createRoute(it))
                }
            }
            composable(
                route = Screen.KonsultasiThread.route,
                arguments = listOf(navArgument("threadId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("threadId") ?: ""
                KonsultasiDetailScreen(
                    threadID = id,
                )
            }

            composable(Screen.Chatbot.route) {
                ChatbotScreen()
            }

            composable(Screen.Riwayat.route) {
                //SiswaRiwayatScreen()
                RiwayatScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    modifier = modifier,
                    navigateToFormTambahDataOrtu = { navController.navigate(Screen.FormTambahDataOrtu.route) }
                )
            }
            composable(Screen.FormTambahDataOrtu.route) {
                FormEditEmailOrtu(
                    onClick = { navController.popBackStack() }
                )
            }
        }
    }
}