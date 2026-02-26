package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.domain.model.LocationStatus

@Entity(
    tableName = "attendance_log",
    indices = [Index(value = ["employeeId", "date"], unique = true)]
)
data class AttendanceEntity(
    @PrimaryKey val logId: String,
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
    val syncStatus: String, // PENDING, SYNCED, FAILED
    val createdAt: Long,
    val updatedAt: Long
)
