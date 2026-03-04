package app.skeleton.sporttrackerskeleton.data.model

import java.time.LocalDateTime

data class CompleteWorkoutModel(
    val id: Long,
    val name: String,
    val duration: Double,
    val endTimestamp: LocalDateTime,
)