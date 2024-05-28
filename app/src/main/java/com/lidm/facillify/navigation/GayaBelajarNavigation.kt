package com.lidm.facillify.navigation

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lidm.facillify.navigation.utils.Screen
import com.lidm.facillify.ui.siswa.gayabelajar.GayaBelajarOnBoardScreen
import com.lidm.facillify.ui.siswa.gayabelajar.GayaBelajarResultScreen
import com.lidm.facillify.ui.siswa.gayabelajar.GayaBelajarTest

@Preview
@Composable
fun GayaBelajarNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = Color.White
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.GayaBelajarInterface.route,
            modifier = modifier.padding(innerPadding),
            enterTransition = {
                slideInHorizontally(initialOffsetX = {1000}) + fadeIn()
            },
            exitTransition = {
                fadeOut()
            },
            popEnterTransition = {
                scaleIn(initialScale = 0.8f) + fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            }
        ) {
            //test gaya belajar
            composable(Screen.GayaBelajarInterface.route) {
                GayaBelajarOnBoardScreen(
                    onNavigateToTestForm = { navController.navigate(Screen.GayaBelajarTest.route) }
                )
            }
            composable(Screen.GayaBelajarTest.route) {
                GayaBelajarTest(
                    onNavigateToTestResult = { navController.navigate(Screen.GayaBelajarTestResult.route) }
                )
            }
            composable(Screen.GayaBelajarTestResult.route) {
                GayaBelajarResultScreen(
                    onNavigateToHome = { /*TODO*/ }
                )
            }
        }
    }
}