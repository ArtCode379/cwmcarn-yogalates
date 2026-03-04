package app.skeleton.sporttrackerskeleton.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.skeleton.sporttrackerskeleton.data.dao.CompleteWorkoutDao
import app.skeleton.sporttrackerskeleton.data.entity.CompleteWorkoutEntity

@Database(
    entities = [CompleteWorkoutEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun statisticsDao(): CompleteWorkoutDao
}