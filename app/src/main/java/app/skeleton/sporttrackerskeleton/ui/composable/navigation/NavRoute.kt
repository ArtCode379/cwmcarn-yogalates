package app.skeleton.sporttrackerskeleton.ui.composable.navigation

import kotlinx.serialization.Serializable

sealed class NavRoute {

    @Serializable
    object Workout : NavRoute()

    @Serializable
    object History : NavRoute()

    @Serializable
    object Statistics : NavRoute()

    @Serializable
    object Settings : NavRoute()

    @Serializable
    object Welcome : NavRoute()

    @Serializable
    object SignUp : NavRoute()

    @Serializable
    object UserProfile : NavRoute()

    @Serializable
    data class WorkoutDetail(val id: Int) : NavRoute()
}