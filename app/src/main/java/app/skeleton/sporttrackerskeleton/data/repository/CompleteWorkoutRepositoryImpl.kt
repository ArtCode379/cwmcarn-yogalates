package app.skeleton.sporttrackerskeleton.data.repository

import app.skeleton.sporttrackerskeleton.data.dao.CompleteWorkoutDao
import app.skeleton.sporttrackerskeleton.data.mapper.CompleteWorkoutMapper
import app.skeleton.sporttrackerskeleton.data.model.CompleteWorkoutModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate

class CompleteWorkoutRepositoryImpl(
    private val completeWorkoutDao: CompleteWorkoutDao,
    private val completeWorkoutMapper: CompleteWorkoutMapper,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CompleteWorkoutRepository {

    override suspend fun save(workout: CompleteWorkoutModel): Long =
        withContext(coroutineDispatcher) {
            completeWorkoutDao.save(
                completeWorkoutMapper.mapToEntity(workout)
            )
        }

    override fun observeAllByDate(date: LocalDate): Flow<List<CompleteWorkoutModel>> {
        val startOfDay = date.atStartOfDay()
        val startOfNextDay = date.plusDays(1).atStartOfDay()

        return completeWorkoutDao.observeAllByDate(startOfDay, startOfNextDay)
            .map { list -> list.map(completeWorkoutMapper::mapToModel) }
    }

    override fun observeAll(): Flow<List<CompleteWorkoutModel>> =
        completeWorkoutDao.observeAll()
            .map { entities ->
                entities.map(completeWorkoutMapper::mapToModel)
            }

    override suspend fun getAll(): List<CompleteWorkoutModel> =
        withContext(coroutineDispatcher) {
            completeWorkoutDao.getAll()
                .map(completeWorkoutMapper::mapToModel)
        }

    override fun observeById(id: Long): Flow<CompleteWorkoutModel?> =
        completeWorkoutDao.observeById(id)
            .map { entity ->
                entity?.let(completeWorkoutMapper::mapToModel)
            }

    override suspend fun getById(id: Long): CompleteWorkoutModel? =
        withContext(coroutineDispatcher) {
            completeWorkoutDao.getById(id)
                ?.let(completeWorkoutMapper::mapToModel)
        }

    override suspend fun deleteById(id: Long) =
        withContext(coroutineDispatcher) {
            completeWorkoutDao.deleteById(id)
        }
}