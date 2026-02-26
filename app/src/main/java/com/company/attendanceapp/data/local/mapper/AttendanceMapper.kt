package com.company.attendanceapp.data.local.mapper

import com.company.attendanceapp.data.local.database.entities.AttendanceEntity
import com.company.attendanceapp.domain.model.Attendance
import com.company.attendanceapp.domain.model.SyncStatus

fun AttendanceEntity.toDomain(): Attendance {
    return Attendance(
        logId = logId,
        employeeId = employeeId,
        date = date,
        checkInTime = checkInTime,
        checkOutTime = checkOutTime,
        checkInLatitude = checkInLatitude,
        checkInLongitude = checkInLongitude,
        checkOutLatitude = checkOutLatitude,
        checkOutLongitude = checkOutLongitude,
        checkInAccuracy = checkInAccuracy,
        locationStatus = locationStatus,
        hoursWorked = hoursWorked,
        breakMinutes = breakMinutes,
        overtimeHours = overtimeHours,
        lateMinutes = lateMinutes,
        earlyDepartureMinutes = earlyDepartureMinutes,
        status = status,
        deviceId = deviceId,
        ipAddress = ipAddress,
        selfieUrl = selfieUrl,
        notes = notes,
        overrideApprovedBy = overrideApprovedBy,
        syncStatus = SyncStatus.valueOf(syncStatus),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Attendance.toEntity(): AttendanceEntity {
    return AttendanceEntity(
        logId = logId,
        employeeId = employeeId,
        date = date,
        checkInTime = checkInTime,
        checkOutTime = checkOutTime,
        checkInLatitude = checkInLatitude,
        checkInLongitude = checkInLongitude,
        checkOutLatitude = checkOutLatitude,
        checkOutLongitude = checkOutLongitude,
        checkInAccuracy = checkInAccuracy,
        locationStatus = locationStatus,
        hoursWorked = hoursWorked,
        breakMinutes = breakMinutes,
        overtimeHours = overtimeHours,
        lateMinutes = lateMinutes,
        earlyDepartureMinutes = earlyDepartureMinutes,
        status = status,
        deviceId = deviceId,
        ipAddress = ipAddress,
        selfieUrl = selfieUrl,
        notes = notes,
        overrideApprovedBy = overrideApprovedBy,
        syncStatus = syncStatus.name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
