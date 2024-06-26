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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lidm.facillify.navigation.utils.Screen
import com.lidm.facillify.ui.components.MainBottomAppBar
import com.lidm.facillify.ui.components.MainFab
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.guru.latihansoal.BaseLatihanSoalGuruScreen
import com.lidm.facillify.ui.guru.latihansoal.TambahLatianSoalGuruScreen
import com.lidm.facillify.ui.guru.materibelajar.DetailMateriBelajarGuruScreen
import com.lidm.facillify.ui.guru.materibelajar.DetailMateriGuru
import com.lidm.facillify.ui.guru.materibelajar.MateriBelajarGuruScreen
import com.lidm.facillify.ui.guru.materibelajar.TambahMateriBelajarScreen
import com.lidm.facillify.ui.konsultasi.KonsultasiDetailScreen
import com.lidm.facillify.ui.konsultasi.KonsultasiForumScreen
import com.lidm.facillify.ui.profile.ProfileScreen
import com.lidm.facillify.ui.siswa.belajar.BelajarScreen
import com.lidm.facillify.ui.tracking.DetailTrackingAnakScreen
import com.lidm.facillify.ui.tracking.TrackingAnakScreen
import com.lidm.facillify.util.Role

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GuruNavigation(
    email: String,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val role = Role.TEACHER

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            MainBottomAppBar(
                navController = navController,
                modifier = modifier,
                role = role,
                hideBottomBar = !(currentRoute == Screen.Belajar.route || currentRoute == Screen.Konsultasi.route || currentRoute == Screen.TrackingList.route),
            )
        },
        topBar = {
            MainTopAppBar(
                onBackClick = { navController.popBackStack() },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                sectionTitle = when (currentRoute) {
                    //Screen.Belajar.route -> "Halo, ${email}"
                    Screen.MateriBelajar.route -> "Materi Belajar"
                    Screen.SiswaMateriBelajarDetail.route -> "Detail Materi"
                    Screen.TambahMateri.route -> "Upload Materi"
                    Screen.Latihan.route -> "Latihan Siswa"
                    Screen.TambahLatihan.route -> "Upload Latihan"
                    Screen.Konsultasi.route -> "Forum Konsultasi"
                    Screen.Chat.route -> "Detail Konsultasi"
                    Screen.TrackingList.route -> "Tracking Siswa"
                    Screen.TrackingDetail.route -> "Perkembangan Siswa"
                    Screen.Profile.route -> "Profile"
                    else -> ""
                },
                backIcon = when (currentRoute) {
                    Screen.Belajar.route -> false
                    Screen.Konsultasi.route -> false
                    Screen.TrackingList.route -> false
                    else -> true
                },
                profileIcon = when (currentRoute) {
                    Screen.Belajar.route -> true
                    Screen.Konsultasi.route -> true
                    Screen.TrackingList.route -> true
                    else -> false
                },
                isHide = currentRoute == Screen.Chat.route || currentRoute == Screen.TrackingDetail.route,
                isHome = currentRoute == Screen.Belajar.route
            )
        },
        floatingActionButton = {
            MainFab(
                onClick = {
                    when (currentRoute) {
                        Screen.MateriBelajar.route -> {
                            navController.navigate(Screen.TambahMateri.route)
                        }

                        Screen.Latihan.route -> {
                            navController.navigate(Screen.TambahLatihan.route)
                        }
                    }
                },
                isShown = currentRoute == Screen.MateriBelajar.route || currentRoute == Screen.Latihan.route
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Belajar.route,
            modifier = modifier.padding(innerPadding),
            enterTransition = {
                when (targetState.destination.route) {
                    Screen.Belajar.route, Screen.Konsultasi.route, Screen.TrackingList.route -> slideInHorizontally(
                        initialOffsetX = { 1000 }) + fadeIn()

                    else -> scaleIn(initialScale = 0.8f) + fadeIn()
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Belajar.route, Screen.Konsultasi.route, Screen.TrackingList.route -> slideOutHorizontally(
                        targetOffsetX = { -1000 }) + fadeOut()

                    else -> fadeOut()
                }
            },
            popEnterTransition = {
                when (targetState.destination.route) {
                    Screen.Belajar.route ->
                        if (
                            initialState.destination.route == Screen.TrackingList.route ||
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
            composable(Screen.Belajar.route) {
                BelajarScreen(
                    modifier = modifier,
                    onBelajarClick = { navController.navigate(Screen.MateriBelajar.route) },
                    onLatihanClick = { navController.navigate(Screen.Latihan.route) }
                )
            }
            composable(Screen.Latihan.route) {
                BaseLatihanSoalGuruScreen()
            }
            composable(Screen.MateriBelajar.route) {
                MateriBelajarGuruScreen(
                    onItemMateriBelajarClicked = {
                        navController.navigate(Screen.SiswaMateriBelajarDetail.createRoute(it))
                    }
                )
            }

            composable(
                route = Screen.SiswaMateriBelajarDetail.route,
                arguments = listOf(navArgument("materiId") { type = NavType.StringType })
            ){
                val id = it.arguments?.getString("materiId") ?: ""
                DetailMateriBelajarGuruScreen(materiId = id, modifier = modifier)
            }

            composable(Screen.TambahMateri.route) {
                TambahMateriBelajarScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.TambahLatihan.route) {
                TambahLatianSoalGuruScreen(
                    onBackClick = {navController.popBackStack()}
                )
            }
            //KONSULTASI -> THREAD
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
            composable(Screen.TrackingList.route) {
                TrackingAnakScreen(onDetailClick = {
                    navController.navigate(Screen.TrackingDetail.createRoute(it))
                }, role = role)
            }
            composable(Screen.TrackingDetail.route) {
                val email: String = it.arguments?.getString("studentEmail") ?: ""
                DetailTrackingAnakScreen(
                    onClickBack = { navController.popBackStack() },
                    emailStudent = email,
                    userRole = role
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(modifier = modifier)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewGuruNavigation() {
    GuruNavigation(
        email = "james.francis.byrnes@example-pet-store.com"
    )
}