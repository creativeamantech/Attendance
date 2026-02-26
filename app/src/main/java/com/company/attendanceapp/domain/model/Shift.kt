package com.company.attendanceapp.domain.model

import java.time.DayOfWeek

enum class ShiftType {
    FIXED,
    FLEXIBLE,
    ROTATING,
    SPLIT
}

data class Shift(
    val shiftId: String,
    val shiftName: String,
    val shiftType: ShiftType,
    val startTimeHour: Int,
    val startTimeMinute: Int,
    val endTimeHour: Int,
    val endTimeMinute: Int,
    val breakMinutes: Int,
    val workingDays: List<DayOfWeek>,
    val gracePeriodMinutes: Int,
    val minHours: Double,
    val overtimeThresholdHours: Double,
    val isNightShift: Boolean,
    val timezone: String
)
