package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.company.attendanceapp.domain.model.ShiftType

@Entity(tableName = "shifts")
data class ShiftEntity(
    @PrimaryKey val shiftId: String,
    val shiftName: String,
    val shiftType: ShiftType,
    val startTimeHour: Int,
    val startTimeMinute: Int,
    val endTimeHour: Int,
    val endTimeMinute: Int,
    val breakMinutes: Int,
    val workingDays: String, // Comma separated DayOfWeek
    val gracePeriodMinutes: Int,
    val minHours: Double,
    val overtimeThresholdHours: Double,
    val isNightShift: Boolean,
    val timezone: String
)
