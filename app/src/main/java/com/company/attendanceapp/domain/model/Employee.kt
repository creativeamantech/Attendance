package com.company.attendanceapp.domain.model

data class Employee(
    val employeeId: String,
    val name: String,
    val email: String,
    val phone: String,
    val department: String,
    val role: String, // "EMPLOYEE", "ADMIN", "HR"
    val managerId: String?,
    val shiftId: String?,
    val locationId: String?,
    val remoteAllowed: Boolean,
    val probationFlag: Boolean,
    val status: String, // "ACTIVE", "INACTIVE"
    val profilePhotoUrl: String?,
    val createdAt: Long
)
