package app.skeleton.sporttrackerskeleton.ui.composable.approot

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import app.skeleton.sporttrackerskeleton.R
import app.skeleton.sporttrackerskeleton.ui.composable.navigation.NavRoute
import app.skeleton.sporttrackerskeleton.ui.theme.Border
import app.skeleton.sporttrackerskeleton.ui.theme.TextPrimary
import app.skeleton.sporttrackerskeleton.ui.theme.TopBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentDestination: NavDestination?,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopBottomBar,
            titleContentColor = TextPrimary,
        ),
        title = {
            Text(
                text = getTitle(currentDestination)?.let { stringResource(it) }.orEmpty()
            )
        }
    )
}

private fun getTitle(currentDestination: NavDestination?): Int? {
    return when {
        currentDestination == null -> null

        currentDestination.hierarchy.any { it.hasRoute(NavRoute.Workout::class) } -> {
            R.string.workout_screen_title
        }

        currentDestination.hierarchy.any { it.hasRoute(NavRoute.WorkoutDetail::class) } -> {
            R.string.workout_details_screen_title
        }

        currentDestination.hierarchy.any { it.hasRoute(NavRoute.History::class) } -> {
            R.string.history_screen_title
        }

        currentDestination.hierarchy.any { it.hasRoute(NavRoute.Statistics::class) } -> {
            R.string.statistics_screen_title
        }

        currentDestination.hierarchy.any { it.hasRoute(NavRoute.Settings::class) } -> {
            R.string.settings_screen_title
        }

        currentDestination.hierarchy.any { it.hasRoute(NavRoute.UserProfile::class) } -> {
            R.string.user_profile_screen_title
        }

        else -> null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutTopBar(
    userName: String,
    onLogoClick: () -> Unit,
    borderColor: Color = Border,
    borderThickness: Dp = 1.2.dp,
) {
    Column() {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TopBottomBar,
                titleContentColor = TextPrimary,
            ),
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .clickable { onLogoClick() }
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                            .padding(4.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Welcome back,",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        )

        HorizontalDivider(
            color = borderColor,
            thickness = borderThickness
        )
    }
}

@Composable
fun AppTopBarBorder(
    currentDestination: NavDestination?,
    borderColor: Color = Border,
    borderThickness: Dp = 1.2.dp,
) {
    Column() {
        AppTopBar(
            currentDestination = currentDestination,
        )

        HorizontalDivider(
            color = borderColor,
            thickness = borderThickness
        )
    }
}

@Composable
private fun TitleWithLogo(
    @DrawableRes logoRes: Int,
    currentDestination: NavDestination?
) {
    Row {
        Spacer(modifier = Modifier.width(4.dp))

        Image(
            painter = painterResource(logoRes),
            contentDescription = "App Logo",
            modifier = Modifier.size(36.dp)
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = getTitle(currentDestination)
                ?.let { stringResource(it).uppercase() }
                .orEmpty(),
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Bottom)
        )
    }
}