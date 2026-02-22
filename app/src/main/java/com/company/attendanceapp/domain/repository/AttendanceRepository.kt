package com.company.attendanceapp.domain.repository

import com.company.attendanceapp.core.common.Resource
import com.company.attendanceapp.domain.model.Attendance
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AttendanceRepository {
    fun getAttendanceForDate(employeeId: String, date: LocalDate): Flow<Resource<Attendance?>>
    fun getAttendanceHistory(employeeId: String, fromDate: LocalDate, toDate: LocalDate): Flow<Resource<List<Attendance>>>
    suspend fun checkIn(employeeId: String, lat: Double, lng: Double, accuracy: Float, notes: String?): Resource<Attendance>
    suspend fun checkOut(employeeId: String, lat: Double, lng: Double, accuracy: Float): Resource<Attendance>
}
