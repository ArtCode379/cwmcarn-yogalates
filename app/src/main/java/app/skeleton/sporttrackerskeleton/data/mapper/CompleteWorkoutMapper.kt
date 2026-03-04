package app.skeleton.sporttrackerskeleton.data.mapper

import app.skeleton.sporttrackerskeleton.data.entity.CompleteWorkoutEntity
import app.skeleton.sporttrackerskeleton.data.model.CompleteWorkoutModel

interface CompleteWorkoutMapper {

    fun mapToEntity(completeWorkoutModel: CompleteWorkoutModel): CompleteWorkoutEntity

    fun mapToModel(completeWorkoutEntity: CompleteWorkoutEntity): CompleteWorkoutModel
}

class CompleteWorkoutMapperImpl : CompleteWorkoutMapper {

    override fun mapToEntity(completeWorkoutModel: CompleteWorkoutModel): CompleteWorkoutEntity {
        return CompleteWorkoutEntity(
            id = completeWorkoutModel.id,
            name = completeWorkoutModel.name,
            duration = completeWorkoutModel.duration,
            endTimestamp = completeWorkoutModel.endTimestamp,
        )
    }

    override fun mapToModel(completeWorkoutEntity: CompleteWorkoutEntity): CompleteWorkoutModel {
        return CompleteWorkoutModel(
            id = completeWorkoutEntity.id,
            name = completeWorkoutEntity.name,
            duration = completeWorkoutEntity.duration,
            endTimestamp = completeWorkoutEntity.endTimestamp,
        )
    }
}