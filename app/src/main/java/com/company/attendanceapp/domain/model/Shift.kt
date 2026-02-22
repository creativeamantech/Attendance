package com.company.attendanceapp.domain.model

import java.time.LocalTime

data class Shift(
    val shiftId: String,
    val shiftName: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val breakMinutes: Int,
    val gracePeriodMinutes: Int,
    val minHours: Double,
    val overtimeThresholdHours: Double,
    val isNightShift: Boolean,
    val flexibleFlag: Boolean
)
