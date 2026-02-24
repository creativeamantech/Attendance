package com.company.attendanceapp.domain.model

data class Shift(
    val shiftId: String,
    val name: String,
    val startTime: String, // HH:mm
    val endTime: String,   // HH:mm
    val breakDuration: Int, // minutes
    val gracePeriod: Int    // minutes
)
