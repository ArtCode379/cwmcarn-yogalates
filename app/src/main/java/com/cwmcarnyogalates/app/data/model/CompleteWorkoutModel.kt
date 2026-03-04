package com.cwmcarnyogalates.app.data.model

import java.time.LocalDate

data class CompleteWorkoutModel(
    val id: Long,
    val workoutType: WorkoutType,
    val durationMinutes: Int,
    val date: LocalDate,
    val notes: String,
)
