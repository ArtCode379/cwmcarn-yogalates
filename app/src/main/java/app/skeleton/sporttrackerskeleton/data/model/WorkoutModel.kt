package app.skeleton.sporttrackerskeleton.data.model

data class WorkoutModel(
    val id: Int,
    val name: String,
    val duration: Double,
    val type: WorkoutType,
    val level: WorkoutLevel,
    val exercises: List<ExerciseModel>,
)