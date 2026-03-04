package app.skeleton.sporttrackerskeleton.data.repository

import app.skeleton.sporttrackerskeleton.data.model.CompleteWorkoutModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CompleteWorkoutRepository {

    suspend fun save(workout: CompleteWorkoutModel): Long

    fun observeAllByDate(date: LocalDate): Flow<List<CompleteWorkoutModel>>

    fun observeAll(): Flow<List<CompleteWorkoutModel>>

    suspend fun getAll(): List<CompleteWorkoutModel>

    fun observeById(id: Long): Flow<CompleteWorkoutModel?>

    suspend fun getById(id: Long): CompleteWorkoutModel?

    suspend fun deleteById(id: Long)
}