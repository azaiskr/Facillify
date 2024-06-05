package com.lidm.facillify.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lidm.facillify.navigation.utils.Screen
import com.lidm.facillify.ui.login.LoginScreen
import com.lidm.facillify.ui.onboarding.OnboardingScreen
import com.lidm.facillify.ui.signup.SignUpInputScreen
import com.lidm.facillify.ui.signup.SignUpScreen
import com.lidm.facillify.util.Role

@Preview
@Composable
fun AuthNavigation(
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
            startDestination = Screen.OnBoarding.route,
            modifier = modifier.padding(innerPadding),
            enterTransition = {
                when (targetState.destination.route) {
                    Screen.Login.route -> {
                        if (initialState.destination.route == Screen.Register.route) {
                            slideInVertically(initialOffsetY = { 1000 }) + fadeIn()
                        } else
                            slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
                    }

                    Screen.Register.route -> slideInVertically(initialOffsetY = { 1000 }) + fadeIn()
                    else -> scaleIn(initialScale = 0.8f) + fadeIn()
                }
            },
            exitTransition = {
                fadeOut()
            },
            popEnterTransition = {
                when (targetState.destination.route) {
                    Screen.Login.route -> {
                        if (initialState.destination.route == Screen.Register.route) {
                            slideInVertically(initialOffsetY = { -1000 }) + fadeIn()
                        } else
                            scaleIn(initialScale = 0.8f) + fadeIn()
                    }

                    else -> scaleIn(initialScale = 0.8f) + fadeIn()
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screen.Login.route -> fadeOut()
                    else -> slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
                }

            }
        ) {
            composable(Screen.OnBoarding.route) {
                OnboardingScreen(
                    onSkip = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.OnBoarding.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    onLogin = {  },
                    onSignUp = { navController.navigate(Screen.Register.route) }
                )
            }
            composable(Screen.Register.route) {
                SignUpScreen(
                    onSignUp = { role ->
                        navController.navigate(Screen.RegisterAs.createRoute(role.toString()))
                    },
                    onSignIn = { navController.popBackStack() },
                )
            }
            composable(
                route = Screen.RegisterAs.route,
                arguments = listOf(navArgument("role") { type = NavType.StringType })
            ) {
                val role = it.arguments?.getString("role")
                SignUpInputScreen(
                    selectedRole = role?.let { Role.valueOf(role) } ?: Role.STUDENT,
                    onSignUp = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Login.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    }
}