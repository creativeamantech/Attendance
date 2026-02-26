package com.company.attendanceapp.domain.repository

import com.company.attendanceapp.domain.model.Attendance
import com.company.attendanceapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    fun checkIn(employeeId: String, latitude: Double, longitude: Double, accuracy: Float, deviceId: String): Flow<Resource<Attendance>>
    fun checkOut(employeeId: String, latitude: Double, longitude: Double, accuracy: Float): Flow<Resource<Attendance>>
    fun getAttendanceByDate(employeeId: String, date: String): Flow<Resource<Attendance?>>
    fun getAttendanceHistory(employeeId: String, fromDate: String, toDate: String): Flow<Resource<List<Attendance>>>
    fun getTodayAttendance(employeeId: String): Flow<Resource<Attendance?>>
    fun updateAttendance(attendance: Attendance): Flow<Resource<Unit>>
    fun getPendingSyncQueue(): Flow<List<Attendance>>
    fun markSynced(logId: String): Flow<Resource<Unit>>
}
