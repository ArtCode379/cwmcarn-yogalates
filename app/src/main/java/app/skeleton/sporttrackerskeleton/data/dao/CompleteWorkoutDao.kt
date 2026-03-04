package app.skeleton.sporttrackerskeleton.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.skeleton.sporttrackerskeleton.data.entity.CompleteWorkoutEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface CompleteWorkoutDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(workout: CompleteWorkoutEntity): Long

    @Query(
        """
        SELECT * FROM complete_workouts
        WHERE end_timestamp >= :startOfDay AND end_timestamp < :startOfNextDay
    """
    )
    fun observeAllByDate(
        startOfDay: LocalDateTime,
        startOfNextDay: LocalDateTime
    ): Flow<List<CompleteWorkoutEntity>>

    @Query("SELECT * FROM complete_workouts")
    fun observeAll(): Flow<List<CompleteWorkoutEntity>>

    @Query("SELECT * FROM complete_workouts")
    suspend fun getAll(): List<CompleteWorkoutEntity>

    @Query("SELECT * FROM complete_workouts WHERE id = :id")
    fun observeById(id: Long): Flow<CompleteWorkoutEntity?>

    @Query("SELECT * FROM complete_workouts WHERE id = :id")
    suspend fun getById(id: Long): CompleteWorkoutEntity?

    @Query("DELETE FROM complete_workouts WHERE id = :id")
    suspend fun deleteById(id: Long)
}