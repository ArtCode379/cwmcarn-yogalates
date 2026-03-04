package com.cwmcarnyogalates.app.ui.composable.approot

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cwmcarnyogalates.app.R
import com.cwmcarnyogalates.app.ui.composable.navigation.AppNavHost
import com.cwmcarnyogalates.app.ui.composable.navigation.BottomNavigationItem
import com.cwmcarnyogalates.app.ui.composable.navigation.NavRoute
import com.cwmcarnyogalates.app.ui.theme.CWMYLTheme
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLAppViewModel
import org.koin.androidx.compose.koinViewModel

private val bottomNavItems = listOf(
    BottomNavigationItem(R.string.nav_home, Icons.Default.Home, NavRoute.Home),
    BottomNavigationItem(R.string.nav_workout, Icons.Default.AddCircle, NavRoute.Workout),
    BottomNavigationItem(R.string.nav_history, Icons.Default.DateRange, NavRoute.History),
    BottomNavigationItem(R.string.nav_statistics, Icons.AutoMirrored.Filled.List, NavRoute.Statistics),
    BottomNavigationItem(R.string.nav_settings, Icons.Default.Settings, NavRoute.Settings),
)

private val hiddenBottomBarScreens = listOf(NavRoute.SignUp::class, NavRoute.UserProfile::class)

@Composable
fun AppRoot(
    modifier: Modifier = Modifier,
    viewModel: CWMYLAppViewModel = koinViewModel(),
) {
    val isDark by viewModel.isDarkTheme.collectAsStateWithLifecycle()
    CWMYLTheme(darkTheme = isDark) {
        AppRootContent(modifier = modifier)
    }
}

@Composable
private fun AppRootContent(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDest = backStackEntry?.destination

    val showBottomBar = currentDest?.let { dest ->
        hiddenBottomBarScreens.none { dest.hasRoute(it) }
    } ?: false

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDest?.hasRoute(item.route::class) == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(NavRoute.Home) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(item.icon, contentDescription = stringResource(item.titleRes))
                            },
                            label = { Text(stringResource(item.titleRes)) },
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        )
    }
}
