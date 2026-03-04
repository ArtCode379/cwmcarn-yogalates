package app.skeleton.sporttrackerskeleton.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity("complete_workouts")
data class CompleteWorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val duration: Double,
    @ColumnInfo(name = "end_timestamp") val endTimestamp: LocalDateTime,
)
