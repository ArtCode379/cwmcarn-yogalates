package app.skeleton.sporttrackerskeleton.data.repository

import app.skeleton.sporttrackerskeleton.data.model.WorkoutModel
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    fun observeAll(): Flow<List<WorkoutModel>>

    suspend fun getAll(): List<WorkoutModel>

    fun observeById(id: Int): Flow<WorkoutModel?>

    suspend fun getById(id: Int): WorkoutModel?
}