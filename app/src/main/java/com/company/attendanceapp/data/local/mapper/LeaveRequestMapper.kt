package com.company.attendanceapp.data.local.mapper

import com.company.attendanceapp.data.local.database.entities.LeaveRequestEntity
import com.company.attendanceapp.domain.model.LeaveRequest

fun LeaveRequestEntity.toDomain(): LeaveRequest {
    return LeaveRequest(
        leaveId = leaveId,
        employeeId = employeeId,
        leaveType = leaveType,
        fromDate = fromDate,
        toDate = toDate,
        halfDayFlag = halfDayFlag,
        halfDayPeriod = halfDayPeriod,
        reason = reason,
        attachmentUrl = attachmentUrl,
        status = status,
        appliedAt = appliedAt,
        approvedBy = approvedBy,
        approvedAt = approvedAt,
        rejectionReason = rejectionReason
    )
}

fun LeaveRequest.toEntity(): LeaveRequestEntity {
    return LeaveRequestEntity(
        leaveId = leaveId,
        employeeId = employeeId,
        leaveType = leaveType,
        fromDate = fromDate,
        toDate = toDate,
        halfDayFlag = halfDayFlag,
        halfDayPeriod = halfDayPeriod,
        reason = reason,
        attachmentUrl = attachmentUrl,
        status = status,
        appliedAt = appliedAt,
        approvedBy = approvedBy,
        approvedAt = approvedAt,
        rejectionReason = rejectionReason
    )
}
