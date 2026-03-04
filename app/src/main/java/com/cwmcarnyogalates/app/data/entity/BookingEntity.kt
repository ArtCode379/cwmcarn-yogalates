package com.cwmcarnyogalates.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "yoga_type") val yogaType: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "instructor_name") val instructorName: String,
    @ColumnInfo(name = "notes") val notes: String,
    @ColumnInfo(name = "status") val status: String,
)
