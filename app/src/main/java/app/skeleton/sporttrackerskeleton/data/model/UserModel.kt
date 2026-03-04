package app.skeleton.sporttrackerskeleton.data.model

data class UserModel(
    val name: String,
    val height: Int,
    val weight: Float,
    val age: Int = 0,
    val goalWorkouts: Int = 0,
)