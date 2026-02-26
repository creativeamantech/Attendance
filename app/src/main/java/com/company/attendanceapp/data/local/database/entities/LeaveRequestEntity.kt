package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.company.attendanceapp.domain.model.HalfDayPeriod
import com.company.attendanceapp.domain.model.LeaveStatus
import com.company.attendanceapp.domain.model.LeaveType

@Entity(tableName = "leave_requests")
data class LeaveRequestEntity(
    @PrimaryKey val leaveId: String,
    val employeeId: String,
    val leaveType: LeaveType,
    val fromDate: String,
    val toDate: String,
    val halfDayFlag: Boolean,
    val halfDayPeriod: HalfDayPeriod?,
    val reason: String,
    val attachmentUrl: String?,
    val status: LeaveStatus,
    val appliedAt: Long,
    val approvedBy: String?,
    val approvedAt: Long?,
    val rejectionReason: String?
)
