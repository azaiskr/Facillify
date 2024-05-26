package com.lidm.facillify.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lidm.facillify.navigation.utils.Screen
import com.lidm.facillify.ui.chat.konsultasi.ChatKonsultasi
import com.lidm.facillify.ui.chat.konsultasi.ListKonsultasi
import com.lidm.facillify.ui.components.MainBottomAppBar
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.profile.ProfileScreen
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.tracking.DetailTrackingScreen
import com.lidm.facillify.ui.tracking.ListTrackingScreen
import com.lidm.facillify.util.Role

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrtuNavigation(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val role = Role.PARENT

    Scaffold(
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
                    Screen.Konsultasi.route -> "Konsultasi"
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
        ) {
            composable(Screen.Konsultasi.route) {
                ListKonsultasi(
                    modifier = modifier,
                    onNavigateToChat = { navController.navigate(Screen.Chat.route) })
            }
            composable(Screen.Chat.route) {
                ChatKonsultasi(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.TrackingList.route) {
                ListTrackingScreen(
                    onDetailClick = { navController.navigate(Screen.TrackingDetail.route) }
                )
            }
            composable(Screen.TrackingDetail.route) {
                DetailTrackingScreen(
                    onClickBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    modifier = modifier,
                    role = role,
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun OrtuNavigationPreview() {
    OrtuNavigation()
}