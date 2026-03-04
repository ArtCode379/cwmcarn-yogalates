package app.skeleton.sporttrackerskeleton.ui.composable.approot

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.skeleton.sporttrackerskeleton.R
import app.skeleton.sporttrackerskeleton.ui.composable.navigation.AppNavHost
import app.skeleton.sporttrackerskeleton.ui.composable.navigation.BottomNavigationItem
import app.skeleton.sporttrackerskeleton.ui.composable.navigation.NavRoute
import app.skeleton.sporttrackerskeleton.ui.theme.Background
import app.skeleton.sporttrackerskeleton.ui.viewmodel.AppViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.KClass

private val bottomNaviItems: List<BottomNavigationItem> = listOf(
    BottomNavigationItem(
        titleRes = R.string.bottom_bar_nav_item_workout_title,
        icon = Icons.Default.Home,
        route = NavRoute.Workout
    ),
    BottomNavigationItem(
        titleRes = R.string.bottom_bar_nav_item_history_title,
        icon = Icons.Default.DateRange,
        route = NavRoute.History
    ),
    BottomNavigationItem(
        titleRes = R.string.bottom_bar_nav_item_statistics_title,
        icon = Icons.AutoMirrored.Filled.List,
        route = NavRoute.Statistics
    ),
    BottomNavigationItem(
        titleRes = R.string.bottom_bar_nav_item_settings_title,
        icon = Icons.Default.Settings,
        route = NavRoute.Settings
    ),
)

private val topBarHiddenScreens = listOf(
    NavRoute.SignUp::class,
    NavRoute.Welcome::class,
)

private val bottomBarHiddenScreens = listOf(
    NavRoute.SignUp::class,
    NavRoute.UserProfile::class,
    NavRoute.Welcome::class,
)

@Composable
fun AppRoot(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = koinViewModel()
) {
    val userNameState by viewModel.userNameState.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val onNavigateToRoute = { item: BottomNavigationItem ->
        navController.navigate(item.route) {
            popUpTo(NavRoute.Workout) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

    }

    val isWorkoutScreen = currentDestination?.hasRoute(NavRoute.Workout::class) == true

    val shouldShowTopAppBar = currentDestination.shouldShowScreenPart(topBarHiddenScreens)
    val shouldShowBottomBar = currentDestination.shouldShowScreenPart(bottomBarHiddenScreens)

    Scaffold(
        modifier = modifier,
        topBar = {
            if (shouldShowTopAppBar) {
                when {
                    isWorkoutScreen -> {
                        WorkoutTopBar(
                            userName = userNameState,
                            onLogoClick = {
                                navController.navigate(NavRoute.UserProfile)
                            }
                        )
                    }

                    else -> {
                        AppTopBarBorder(
                            currentDestination = currentDestination,
                        )
                    }
                }
            }
        },

        bottomBar = {
            if (shouldShowBottomBar) {
                AppBottomBar(
                    currentDestination = currentDestination,
                    navigationItems = bottomNaviItems,
                    onNavigateToRoute = onNavigateToRoute
                )
            }
        },
        containerColor = Background,
    ) { paddingValues ->
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        )
    }
}

fun NavDestination?.shouldShowScreenPart(hiddenScreens: List<KClass<*>>): Boolean {
    return this?.let { destination ->
        hiddenScreens.none { destination.hasRoute(it) }
    } ?: false
}