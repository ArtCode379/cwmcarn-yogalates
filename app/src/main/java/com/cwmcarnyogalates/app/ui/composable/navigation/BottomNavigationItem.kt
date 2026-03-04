package com.cwmcarnyogalates.app.ui.composable.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    @field:StringRes val titleRes: Int,
    val icon: ImageVector,
    val route: NavRoute,
)
