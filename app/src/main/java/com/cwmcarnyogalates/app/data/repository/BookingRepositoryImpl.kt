package com.cwmcarnyogalates.app.data.repository

import com.cwmcarnyogalates.app.data.dao.BookingDao
import com.cwmcarnyogalates.app.data.mapper.BookingMapper
import com.cwmcarnyogalates.app.data.model.BookingModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class BookingRepositoryImpl(
    private val dao: BookingDao,
    private val mapper: BookingMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BookingRepository {

    override suspend fun insert(booking: BookingModel): Long =
        withContext(dispatcher) { dao.insert(mapper.mapToEntity(booking)) }

    override fun observeAll(): Flow<List<BookingModel>> =
        dao.observeAll().map { list -> list.map(mapper::mapToModel) }

    override fun observeUpcoming(): Flow<List<BookingModel>> =
        dao.observeUpcoming().map { list -> list.map(mapper::mapToModel) }

    override suspend fun deleteById(id: Long) =
        withContext(dispatcher) { dao.deleteById(id) }
}
