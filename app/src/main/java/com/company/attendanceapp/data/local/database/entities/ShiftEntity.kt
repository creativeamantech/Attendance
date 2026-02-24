package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shifts")
data class ShiftEntity(
    @PrimaryKey val shiftId: String,
    val name: String,
    val startTime: String,
    val endTime: String,
    val breakDuration: Int,
    val gracePeriod: Int
)
