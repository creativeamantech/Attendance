package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shifts")
data class ShiftEntity(
    @PrimaryKey val shiftId: String,
    val shiftName: String,
    val startTime: String, // HH:mm
    val endTime: String,
    val breakMinutes: Int,
    val gracePeriodMinutes: Int,
    val minHours: Double,
    val workingDays: String // CSV: MON,TUE...
)
