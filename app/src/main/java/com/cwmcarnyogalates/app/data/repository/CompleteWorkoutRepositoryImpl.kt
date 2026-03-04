package com.cwmcarnyogalates.app.data.repository

import com.cwmcarnyogalates.app.data.dao.CompleteWorkoutDao
import com.cwmcarnyogalates.app.data.mapper.CompleteWorkoutMapper
import com.cwmcarnyogalates.app.data.model.CompleteWorkoutModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate

class CompleteWorkoutRepositoryImpl(
    private val dao: CompleteWorkoutDao,
    private val mapper: CompleteWorkoutMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CompleteWorkoutRepository {

    override suspend fun save(workout: CompleteWorkoutModel): Long =
        withContext(dispatcher) { dao.save(mapper.mapToEntity(workout)) }

    override fun observeAll(): Flow<List<CompleteWorkoutModel>> =
        dao.observeAll().map { list -> list.map(mapper::mapToModel) }

    override suspend fun getAll(): List<CompleteWorkoutModel> =
        withContext(dispatcher) { dao.getAll().map(mapper::mapToModel) }

    override fun observeAllByDate(date: LocalDate): Flow<List<CompleteWorkoutModel>> =
        dao.observeAllByDate(date.toString()).map { list -> list.map(mapper::mapToModel) }

    override suspend fun deleteById(id: Long) =
        withContext(dispatcher) { dao.deleteById(id) }
}
