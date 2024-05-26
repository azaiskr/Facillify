package com.lidm.facillify.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.OnBoarding.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.OnBoarding.route) {
                OnboardingScreen(
                    index = 0,
                    onSkip = { navController.navigate(Screen.Login.route) } //TODO : clear backstack
                )
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    onLogin = {/*TODO*/ },
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
                        navController.popBackStack(
                            Screen.Login.route,
                            inclusive = false
                        )
                    },
                )
            }
        }
    }
}