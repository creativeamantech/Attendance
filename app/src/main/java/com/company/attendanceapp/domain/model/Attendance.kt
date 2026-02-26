package com.company.attendanceapp.domain.model

enum class SyncStatus {
    PENDING,
    SYNCED,
    FAILED
}

data class Attendance(
    val logId: String,
    val employeeId: String,
    val date: String,
    val checkInTime: Long?,
    val checkOutTime: Long?,
    val checkInLatitude: Double?,
    val checkInLongitude: Double?,
    val checkOutLatitude: Double?,
    val checkOutLongitude: Double?,
    val checkInAccuracy: Float?,
    val locationStatus: LocationStatus,
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
    val syncStatus: SyncStatus,
    val createdAt: Long,
    val updatedAt: Long
)
