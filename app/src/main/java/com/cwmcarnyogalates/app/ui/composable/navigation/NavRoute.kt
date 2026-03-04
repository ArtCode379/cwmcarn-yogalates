package com.cwmcarnyogalates.app.ui.composable.navigation

import kotlinx.serialization.Serializable

sealed class NavRoute {
    @Serializable object Home : NavRoute()
    @Serializable object Workout : NavRoute()
    @Serializable object History : NavRoute()
    @Serializable object Statistics : NavRoute()
    @Serializable object Settings : NavRoute()
    @Serializable object SignUp : NavRoute()
    @Serializable object UserProfile : NavRoute()
}
