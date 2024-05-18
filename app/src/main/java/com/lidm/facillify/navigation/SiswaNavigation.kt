package com.lidm.facillify.navigation

import android.annotation.SuppressLint
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
import com.lidm.facillify.ui.siswa.belajar.SiswaBelajarScreen
import com.lidm.facillify.ui.siswa.konsultasi.SiswaKonsultasiScreen

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
            )
        },
        topBar = {
            MainTopAppBar(
                sectionTitle = when (currentRoute) {
                    Screen.SiswaHome.route -> "Home"
                    Screen.SiswaBelajar.route -> "Belajar"
                    Screen.SiswaKonsultasi.route -> "Konsultasi"
                    Screen.SiswaRiwayat.route -> "Riwayat"
                    else -> ""
                },
                backIcon = when (currentRoute) {
                    Screen.SiswaHome.route -> true
                    Screen.SiswaBelajar.route -> false
                    Screen.SiswaKonsultasi.route -> false
                    Screen.SiswaRiwayat.route -> false
                    else -> true
                },
                context = context,
                navController = navController,
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
                )
            }

            composable(Screen.SiswaKonsultasi.route) {
                SiswaKonsultasiScreen()
            }

            composable(Screen.SiswaRiwayat.route) {
                SiswaRiwayatScreen()
            }
        }
    }
}