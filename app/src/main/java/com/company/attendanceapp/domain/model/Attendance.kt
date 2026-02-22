package com.company.attendanceapp.domain.model

import java.time.LocalDateTime

data class Attendance(
    val logId: String,
    val employeeId: String,
    val date: LocalDateTime,
    val checkInTime: LocalDateTime?,
    val checkOutTime: LocalDateTime?,
    val checkInLat: Double?,
    val checkInLng: Double?,
    val checkOutLat: Double?,
    val checkOutLng: Double?,
    val checkInAccuracy: Float?,
    val locationStatus: String, // VALID/OUT_OF_ZONE
    val hoursWorked: Double,
    val breakMinutes: Int,
    val overtimeHours: Double,
    val lateMinutes: Int,
    val earlyDepartureMinutes: Int,
    val status: AttendanceStatus,
    val deviceId: String?,
    val ipAddress: String?,
    val selfieUrl: String?,
    val notes: String?,
    val overrideApprovedBy: String?,
    val syncStatus: String, // SYNCED/PENDING
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
