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
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.konsultasi.KonsultasiDetailScreen
import com.lidm.facillify.ui.konsultasi.KonsultasiForumScreen
import com.lidm.facillify.ui.profile.ProfileScreen
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.tracking.DetailTrackingAnakScreen
import com.lidm.facillify.ui.tracking.TrackingAnakScreen
import com.lidm.facillify.util.Role

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrtuNavigation(
    email: String,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val role = Role.PARENT

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            MainBottomAppBar(
                navController = navController,
                role = role,
                modifier = modifier,
                hideBottomBar = currentRoute == Screen.Chat.route || currentRoute == Screen.TrackingDetail.route || currentRoute == Screen.Profile.route,
            )
        },
        topBar = {
            MainTopAppBar(
                sectionTitle = when (currentRoute) {
                    Screen.Konsultasi.route -> "Halo, ${email}"
                    Screen.TrackingList.route -> "Tracking Anak"
                    Screen.TrackingDetail.route -> "Perkembangan Anak"
                    Screen.Profile.route -> "Profile"
                    else -> ""
                },
                contentColor = if(currentRoute == Screen.TrackingDetail.route) Color.White else DarkBlue,
                backIcon = when (currentRoute) {
                    Screen.Chat.route -> true
                    Screen.TrackingDetail.route -> true
                    Screen.Profile.route -> true
                    else -> false
                },
                onBackClick = { navController.popBackStack() },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                profileIcon = when (currentRoute){
                    Screen.Profile.route -> false
                    else -> true
                },
                isHide = currentRoute == Screen.Chat.route || currentRoute == Screen.TrackingDetail.route
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Konsultasi.route,
            modifier = modifier.padding(innerPadding),
            enterTransition = {
                when (targetState.destination.route) {
                    Screen.Konsultasi.route, Screen.TrackingList.route -> slideInHorizontally(
                        initialOffsetX = { 1000 }) + fadeIn()

                    else -> scaleIn(initialScale = 0.8f) + fadeIn()
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Konsultasi.route, Screen.TrackingList.route -> slideOutHorizontally(
                        targetOffsetX = { -1000 }) + fadeOut()

                    else -> fadeOut()
                }
            },
            popEnterTransition = {
                when (targetState.destination.route) {
                    Screen.Konsultasi.route -> if (
                        initialState.destination.route == Screen.TrackingList.route
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
            /*composable(Screen.Konsultasi.route) {
                ListKonsultasi(
                    modifier = modifier,
                    onNavigateToChat = { navController.navigate(Screen.Chat.route) })
            }
            composable(Screen.Chat.route) {
                ChatKonsultasi(
                    onBackClick = { navController.popBackStack() }
                )
            }*/
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
                ProfileScreen(
                    modifier = modifier,
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun OrtuNavigationPreview() {
    OrtuNavigation(
        email = "helen.herron.taft@my-own-personal-domain.com"
    )
}