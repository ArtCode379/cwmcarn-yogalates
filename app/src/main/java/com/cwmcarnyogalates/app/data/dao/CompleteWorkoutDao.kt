package com.cwmcarnyogalates.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cwmcarnyogalates.app.data.entity.CompleteWorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompleteWorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(workout: CompleteWorkoutEntity): Long

    @Query("SELECT * FROM complete_workouts ORDER BY date DESC")
    fun observeAll(): Flow<List<CompleteWorkoutEntity>>

    @Query("SELECT * FROM complete_workouts ORDER BY date DESC")
    suspend fun getAll(): List<CompleteWorkoutEntity>

    @Query("SELECT * FROM complete_workouts WHERE date = :date")
    fun observeAllByDate(date: String): Flow<List<CompleteWorkoutEntity>>

    @Query("DELETE FROM complete_workouts WHERE id = :id")
    suspend fun deleteById(id: Long)
}
