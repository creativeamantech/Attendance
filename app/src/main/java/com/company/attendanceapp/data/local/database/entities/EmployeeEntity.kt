package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey val employeeId: String,
    val name: String,
    val email: String,
    val phone: String,
    val department: String,
    val role: String,
    val managerId: String?,
    val shiftId: String?,
    val locationId: String?,
    val remoteAllowed: Boolean,
    val probationFlag: Boolean,
    val status: String,
    val profilePhotoUrl: String?,
    val createdAt: Long
)
