package com.company.attendanceapp.domain.model

enum class LeaveType {
    ANNUAL,
    SICK,
    CASUAL,
    WFH,
    COMPENSATORY
}

enum class HalfDayPeriod {
    MORNING,
    AFTERNOON
}

enum class LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED
}

data class LeaveRequest(
    val leaveId: String,
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
