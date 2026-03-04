package com.cwmcarnyogalates.app.data.model

import java.time.LocalDate

data class BookingModel(
    val id: Long,
    val yogaType: WorkoutType,
    val date: LocalDate,
    val time: String,
    val instructorName: String,
    val notes: String,
    val status: BookingStatus,
)
