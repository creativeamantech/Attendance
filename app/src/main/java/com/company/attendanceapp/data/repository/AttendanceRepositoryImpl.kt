package com.company.attendanceapp.data.repository

import com.company.attendanceapp.core.common.Resource
import com.company.attendanceapp.domain.model.Attendance
import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.domain.repository.AttendanceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    // private val attendanceDao: AttendanceDao, // TODO
    // private val sheetsApi: GoogleSheetsApi // TODO
) : AttendanceRepository {

    override fun getAttendanceForDate(employeeId: String, date: LocalDate): Flow<Resource<Attendance?>> = flow {
        emit(Resource.Loading())
        delay(500) // Mock DB read

        // Mock data: Today's attendance
        if (date == LocalDate.now()) {
            val mockAttendance = Attendance(
                logId = "log-001",
                employeeId = employeeId,
                date = LocalDateTime.now(),
                checkInTime = LocalDateTime.now().withHour(9).withMinute(2),
                checkOutTime = null,
                checkInLat = 12.9716,
                checkInLng = 77.5946,
                checkOutLat = null,
                checkOutLng = null,
                checkInAccuracy = 12f,
                locationStatus = "VALID",
                hoursWorked = 2.5,
                breakMinutes = 0,
                overtimeHours = 0.0,
                lateMinutes = 2,
                earlyDepartureMinutes = 0,
                status = AttendanceStatus.PRESENT,
                deviceId = "device-123",
                ipAddress = "192.168.1.5",
                selfieUrl = null,
                notes = null,
                overrideApprovedBy = null,
                syncStatus = "PENDING",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
            emit(Resource.Success(mockAttendance))
        } else {
            emit(Resource.Success(null))
        }
    }

    override fun getAttendanceHistory(
        employeeId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Flow<Resource<List<Attendance>>> = flow {
        emit(Resource.Loading())
        delay(1000)

        // Mock list
        val history = listOf<Attendance>() // Empty for now
        emit(Resource.Success(history))
    }

    override suspend fun checkIn(
        employeeId: String,
        lat: Double,
        lng: Double,
        accuracy: Float,
        notes: String?
    ): Resource<Attendance> {
        delay(1000)
        // Return success with mock object
        return Resource.Success(
            Attendance(
                logId = "new-log",
                employeeId = employeeId,
                date = LocalDateTime.now(),
                checkInTime = LocalDateTime.now(),
                checkOutTime = null,
                checkInLat = lat,
                checkInLng = lng,
                checkOutLat = null,
                checkOutLng = null,
                checkInAccuracy = accuracy,
                locationStatus = "VALID",
                hoursWorked = 0.0,
                breakMinutes = 0,
                overtimeHours = 0.0,
                lateMinutes = 0,
                earlyDepartureMinutes = 0,
                status = AttendanceStatus.PRESENT,
                deviceId = "device-xyz",
                ipAddress = "10.0.0.1",
                selfieUrl = null,
                notes = notes,
                overrideApprovedBy = null,
                syncStatus = "PENDING",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )
    }

    override suspend fun checkOut(
        employeeId: String,
        lat: Double,
        lng: Double,
        accuracy: Float
    ): Resource<Attendance> {
        delay(1000)
        return Resource.Success(
            Attendance(
                logId = "log-001",
                employeeId = employeeId,
                date = LocalDateTime.now(),
                checkInTime = LocalDateTime.now().withHour(9).withMinute(0),
                checkOutTime = LocalDateTime.now(),
                checkInLat = lat,
                checkInLng = lng,
                checkOutLat = lat,
                checkOutLng = lng,
                checkInAccuracy = accuracy,
                locationStatus = "VALID",
                hoursWorked = 9.0,
                breakMinutes = 60,
                overtimeHours = 0.0,
                lateMinutes = 0,
                earlyDepartureMinutes = 0,
                status = AttendanceStatus.PRESENT,
                deviceId = "device-xyz",
                ipAddress = "10.0.0.1",
                selfieUrl = null,
                notes = null,
                overrideApprovedBy = null,
                syncStatus = "SYNCED",
                createdAt = LocalDateTime.now().minusHours(9),
                updatedAt = LocalDateTime.now()
            )
        )
    }
}
