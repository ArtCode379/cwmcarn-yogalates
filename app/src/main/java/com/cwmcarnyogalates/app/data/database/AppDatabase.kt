package com.cwmcarnyogalates.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cwmcarnyogalates.app.data.dao.BookingDao
import com.cwmcarnyogalates.app.data.dao.CompleteWorkoutDao
import com.cwmcarnyogalates.app.data.entity.BookingEntity
import com.cwmcarnyogalates.app.data.entity.CompleteWorkoutEntity

@Database(
    entities = [CompleteWorkoutEntity::class, BookingEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun completeWorkoutDao(): CompleteWorkoutDao

    abstract fun bookingDao(): BookingDao
}
