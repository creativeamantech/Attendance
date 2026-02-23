package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance_log")
data class AttendanceEntity(
    @PrimaryKey val logId: String,
    val employeeId: String,
    val date: String, // ISO Date
    val checkInTime: String?, // ISO DateTime
    val checkOutTime: String?,
    val checkInLat: Double?,
    val checkInLng: Double?,
    val locationStatus: String,
    val status: String, // PRESENT, LATE, etc.
    val syncStatus: String // SYNCED, PENDING
)
