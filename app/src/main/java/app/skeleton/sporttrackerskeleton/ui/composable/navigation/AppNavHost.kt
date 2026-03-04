package app.skeleton.sporttrackerskeleton.ui.composable.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import app.skeleton.sporttrackerskeleton.ui.composable.screen.history.HistoryScreen
import app.skeleton.sporttrackerskeleton.ui.composable.screen.settings.SettingsScreen
import app.skeleton.sporttrackerskeleton.ui.composable.screen.signup.SignUpScreen
import app.skeleton.sporttrackerskeleton.ui.composable.screen.statistics.StatisticsScreen
import app.skeleton.sporttrackerskeleton.ui.composable.screen.usercabinet.UserProfileScreen
import app.skeleton.sporttrackerskeleton.ui.composable.screen.welcome.WelcomeScreen
import app.skeleton.sporttrackerskeleton.ui.composable.screen.workout.WorkoutScreen
import app.skeleton.sporttrackerskeleton.ui.composable.screen.workoutdetails.WorkoutDetailsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Welcome,
        modifier = modifier,
    ) {
        composable<NavRoute.Welcome> {
            WelcomeScreen(
                onNavigateToSignUp = {
                    navController.navigate(NavRoute.SignUp)
                },
                onNavigateToWorkout = {
                    navController.navigate(NavRoute.Workout) {
                        popUpTo(NavRoute.Welcome) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<NavRoute.Workout> {
            WorkoutScreen(
                onNavigateToWorkoutDetails = { id: Int ->
                    navController.navigate(
                        route = NavRoute.WorkoutDetail(id = id)
                    )
                }
            )
        }

        composable<NavRoute.WorkoutDetail> { backStackEntry ->
            val workoutDetail: NavRoute.WorkoutDetail = backStackEntry.toRoute()
            WorkoutDetailsScreen(
                workoutId = workoutDetail.id,
            )
        }

        composable<NavRoute.History> {
            HistoryScreen()
        }

        composable<NavRoute.Statistics> {
            StatisticsScreen()
        }

        composable<NavRoute.Settings> {
            SettingsScreen()
        }

        composable<NavRoute.SignUp> {
            SignUpScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateToWorkoutScreen = {
                    navController.navigate(NavRoute.Workout) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<NavRoute.UserProfile> {
            UserProfileScreen()
        }
    }
}