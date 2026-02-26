package com.company.attendanceapp.domain.model

enum class EmployeeStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED
}

data class Employee(
    val employeeId: String,
    val name: String,
    val email: String,
    val phone: String,
    val department: String,
    val role: String,
    val managerId: String,
    val shiftId: String,
    val locationId: String,
    val remoteAllowed: Boolean,
    val probationFlag: Boolean,
    val status: EmployeeStatus,
    val profilePhotoUrl: String?,
    val createdAt: Long
)
