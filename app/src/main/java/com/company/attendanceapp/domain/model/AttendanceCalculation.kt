package com.company.attendanceapp.domain.model

data class AttendanceCalculation(
    val hoursWorked: Double,
    val lateMinutes: Int,
    val overtimeHours: Double,
    val earlyDepartureMinutes: Int,
    val status: AttendanceStatus,
    val breakMinutes: Int
)
