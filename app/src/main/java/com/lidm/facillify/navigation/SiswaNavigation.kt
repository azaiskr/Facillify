package com.lidm.facillify.navigation

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lidm.facillify.navigation.utils.Screen
import com.lidm.facillify.ui.components.MainBottomAppBar
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.siswa.SiswaHomeScreen
import com.lidm.facillify.ui.siswa.SiswaRiwayatScreen
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarDetailScreen
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarScreen
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarVideoScreen
import com.lidm.facillify.ui.siswa.belajar.SiswaBelajarScreen
import com.lidm.facillify.ui.siswa.konsultasi.SiswaKonsultasiScreen
import com.lidm.facillify.ui.siswa.profile.ProfileScreen

@Preview
@Composable
fun SiswaNavigation(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            MainBottomAppBar(
                navController = navController,
                screen = listOf(
                    Screen.SiswaHome,
                    Screen.SiswaBelajar,
                    Screen.SiswaKonsultasi,
                    Screen.SiswaRiwayat,
                ),
                modifier = modifier,
                hideBottomBar = !(currentRoute == Screen.SiswaHome.route ||
                        currentRoute == Screen.SiswaBelajar.route ||
                        currentRoute == Screen.SiswaKonsultasi.route ||
                        currentRoute == Screen.SiswaRiwayat.route)
            )
        },
        topBar = {
            MainTopAppBar(
                sectionTitle = when (currentRoute) {
                    Screen.SiswaHome.route -> "Home"
                    Screen.SiswaBelajar.route -> "Belajar"
                    Screen.SiswaMateriBelajar.route -> "Materi Belajar"
                    Screen.SiswaMateriBelajarDetail.route -> "Materi Belajar"
                    Screen.SiswaMateriBelajarVideo.route -> "Materi Belajar"
                    Screen.SiswaKonsultasi.route -> "Konsultasi"
                    Screen.SiswaRiwayat.route -> "Riwayat"
                    Screen.SiswaProfile.route -> "Profil Siswa"
                    else -> ""
                },
                backIcon = when (currentRoute) {
                    Screen.SiswaHome.route -> false
                    Screen.SiswaBelajar.route -> false
                    Screen.SiswaKonsultasi.route -> false
                    Screen.SiswaRiwayat.route -> false
                    else -> true
                },
                onBackClick = { navController.popBackStack() },
                onProfileClick = { navController.navigate(Screen.SiswaProfile.route) },
                profileIcon = when (currentRoute) {
                    Screen.SiswaProfile.route -> false
                    else -> true
                }
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SiswaHome.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.SiswaHome.route) {
                SiswaHomeScreen()
            }

            composable(Screen.SiswaBelajar.route) {
                SiswaBelajarScreen(
                    modifier = modifier,
                    onBelajarClick = { navController.navigate(Screen.SiswaMateriBelajar.route) },
                    // TODO: onLatihanClick
                )
            }
            composable(Screen.SiswaMateriBelajar.route) {
                MateriBelajarScreen()
            }
            composable(Screen.SiswaMateriBelajarDetail.route){
                MateriBelajarDetailScreen(
                    modifier = modifier,
                    onNavigateToMateriVideo = {navController.navigate(Screen.SiswaMateriBelajarVideo.route)}
                )
            }
            composable(Screen.SiswaMateriBelajarVideo.route){
                MateriBelajarVideoScreen(modifier = modifier)
            }

            composable(Screen.SiswaKonsultasi.route) {
                SiswaKonsultasiScreen()
            }

            composable(Screen.SiswaRiwayat.route) {
                SiswaRiwayatScreen()
            }
            composable(Screen.SiswaProfile.route) {
                ProfileScreen()
            }
        }
    }
}