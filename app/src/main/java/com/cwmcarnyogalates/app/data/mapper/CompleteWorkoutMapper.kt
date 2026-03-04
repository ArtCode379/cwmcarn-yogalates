package com.cwmcarnyogalates.app.data.mapper

import com.cwmcarnyogalates.app.data.entity.CompleteWorkoutEntity
import com.cwmcarnyogalates.app.data.model.CompleteWorkoutModel
import com.cwmcarnyogalates.app.data.model.WorkoutType
import java.time.LocalDate

interface CompleteWorkoutMapper {
    fun mapToEntity(model: CompleteWorkoutModel): CompleteWorkoutEntity
    fun mapToModel(entity: CompleteWorkoutEntity): CompleteWorkoutModel
}

class CompleteWorkoutMapperImpl : CompleteWorkoutMapper {

    override fun mapToEntity(model: CompleteWorkoutModel): CompleteWorkoutEntity =
        CompleteWorkoutEntity(
            id = model.id,
            workoutType = model.workoutType.name,
            durationMinutes = model.durationMinutes,
            date = model.date.toString(),
            notes = model.notes,
        )

    override fun mapToModel(entity: CompleteWorkoutEntity): CompleteWorkoutModel =
        CompleteWorkoutModel(
            id = entity.id,
            workoutType = WorkoutType.valueOf(entity.workoutType),
            durationMinutes = entity.durationMinutes,
            date = LocalDate.parse(entity.date),
            notes = entity.notes,
        )
}
