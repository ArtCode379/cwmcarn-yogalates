package com.cwmcarnyogalates.app.data.mapper

import com.cwmcarnyogalates.app.data.entity.BookingEntity
import com.cwmcarnyogalates.app.data.model.BookingModel
import com.cwmcarnyogalates.app.data.model.BookingStatus
import com.cwmcarnyogalates.app.data.model.WorkoutType
import java.time.LocalDate

interface BookingMapper {
    fun mapToEntity(model: BookingModel): BookingEntity
    fun mapToModel(entity: BookingEntity): BookingModel
}

class BookingMapperImpl : BookingMapper {

    override fun mapToEntity(model: BookingModel): BookingEntity =
        BookingEntity(
            id = model.id,
            yogaType = model.yogaType.name,
            date = model.date.toString(),
            time = model.time,
            instructorName = model.instructorName,
            notes = model.notes,
            status = model.status.name,
        )

    override fun mapToModel(entity: BookingEntity): BookingModel =
        BookingModel(
            id = entity.id,
            yogaType = WorkoutType.valueOf(entity.yogaType),
            date = LocalDate.parse(entity.date),
            time = entity.time,
            instructorName = entity.instructorName,
            notes = entity.notes,
            status = BookingStatus.valueOf(entity.status),
        )
}
