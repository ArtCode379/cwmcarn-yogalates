package app.skeleton.sporttrackerskeleton.data.model

data class ExerciseModel(
    val name: String,
    val sets: Int,
    val times: Int,
    val weight: Double,
    val restDuration: Double,
)