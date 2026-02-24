package com.company.attendanceapp.domain.repository

import com.company.attendanceapp.domain.model.Attendance
import com.company.attendanceapp.domain.model.AttendanceStatus
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    suspend fun getTodayAttendance(employeeId: String): Attendance?
    suspend fun clockIn(employeeId: String, timestamp: Long, location: String): Result<Attendance>
    suspend fun clockOut(employeeId: String, timestamp: Long, location: String): Result<Attendance>
    suspend fun getAttendanceHistory(employeeId: String, startDate: Long, endDate: Long): Flow<List<Attendance>>
    suspend fun syncAttendance()
}
