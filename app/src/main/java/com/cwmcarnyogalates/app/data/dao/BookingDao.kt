package com.cwmcarnyogalates.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cwmcarnyogalates.app.data.entity.BookingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(booking: BookingEntity): Long

    @Query("SELECT * FROM bookings ORDER BY date ASC, time ASC")
    fun observeAll(): Flow<List<BookingEntity>>

    @Query("SELECT * FROM bookings WHERE status = 'SCHEDULED' ORDER BY date ASC, time ASC")
    fun observeUpcoming(): Flow<List<BookingEntity>>

    @Query("DELETE FROM bookings WHERE id = :id")
    suspend fun deleteById(id: Long)
}
