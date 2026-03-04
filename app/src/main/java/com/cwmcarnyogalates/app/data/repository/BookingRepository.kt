package com.cwmcarnyogalates.app.data.repository

import com.cwmcarnyogalates.app.data.model.BookingModel
import kotlinx.coroutines.flow.Flow

interface BookingRepository {
    suspend fun insert(booking: BookingModel): Long
    fun observeAll(): Flow<List<BookingModel>>
    fun observeUpcoming(): Flow<List<BookingModel>>
    suspend fun deleteById(id: Long)
}
