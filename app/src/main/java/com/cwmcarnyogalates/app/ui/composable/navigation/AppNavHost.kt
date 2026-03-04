package com.cwmcarnyogalates.app.ui.composable.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cwmcarnyogalates.app.ui.composable.screen.history.CWMYLHistoryScreen
import com.cwmcarnyogalates.app.ui.composable.screen.settings.CWMYLSettingsScreen
import com.cwmcarnyogalates.app.ui.composable.screen.signup.CWMYLSignUpScreen
import com.cwmcarnyogalates.app.ui.composable.screen.statistics.CWMYLStatisticsScreen
import com.cwmcarnyogalates.app.ui.composable.screen.usercabinet.CWMYLUserProfileScreen
import com.cwmcarnyogalates.app.ui.composable.screen.welcome.CWMYLWelcomeScreen
import com.cwmcarnyogalates.app.ui.composable.screen.workout.CWMYLWorkoutScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home,
        modifier = modifier,
    ) {
        composable<NavRoute.Home> {
            CWMYLWelcomeScreen(
                onNavigateToSignUp = { navController.navigate(NavRoute.SignUp) },
                onNavigateToHome = {
                    navController.navigate(NavRoute.Home) {
                        popUpTo(NavRoute.Home) { inclusive = true }
                    }
                }
            )
        }

        composable<NavRoute.SignUp> {
            CWMYLSignUpScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateToHome = {
                    navController.navigate(NavRoute.Home) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<NavRoute.Workout> {
            CWMYLWorkoutScreen()
        }

        composable<NavRoute.History> {
            CWMYLHistoryScreen()
        }

        composable<NavRoute.Statistics> {
            CWMYLStatisticsScreen()
        }

        composable<NavRoute.Settings> {
            CWMYLSettingsScreen()
        }

        composable<NavRoute.UserProfile> {
            CWMYLUserProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
