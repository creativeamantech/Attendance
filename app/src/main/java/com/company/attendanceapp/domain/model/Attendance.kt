package com.company.attendanceapp.domain.model

data class Attendance(
    val attendanceId: String,
    val employeeId: String,
    val date: String,
    val clockInTime: Long,
    val clockOutTime: Long?,
    val status: AttendanceStatus,
    val totalHours: Double,
    val overTimeHours: Double
)
