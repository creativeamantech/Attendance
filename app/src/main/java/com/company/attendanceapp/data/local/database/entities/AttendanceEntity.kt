package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance")
data class AttendanceEntity(
    @PrimaryKey val attendanceId: String,
    val employeeId: String,
    val date: String,
    val clockInTime: Long,
    val clockOutTime: Long?,
    val status: String,
    val totalHours: Double,
    val overTimeHours: Double
)
