package com.cwmcarnyogalates.app.data.repository

import com.cwmcarnyogalates.app.data.model.CompleteWorkoutModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CompleteWorkoutRepository {
    suspend fun save(workout: CompleteWorkoutModel): Long
    fun observeAll(): Flow<List<CompleteWorkoutModel>>
    suspend fun getAll(): List<CompleteWorkoutModel>
    fun observeAllByDate(date: LocalDate): Flow<List<CompleteWorkoutModel>>
    suspend fun deleteById(id: Long)
}
