package com.company.attendanceapp.data.repository

import com.company.attendanceapp.data.local.database.dao.AttendanceDao
import com.company.attendanceapp.data.local.database.entities.AttendanceEntity
import com.company.attendanceapp.domain.model.Attendance
import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.domain.repository.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val attendanceDao: AttendanceDao
) : AttendanceRepository {

    override suspend fun getTodayAttendance(employeeId: String): Attendance? {
        val today = java.time.LocalDate.now().toString() // Simple date string for now
        return attendanceDao.getAttendanceByDate(employeeId, today)?.toDomain()
    }

    override suspend fun clockIn(employeeId: String, timestamp: Long, location: String): Result<Attendance> {
        // Logic to create attendance entity
        val today = java.time.LocalDate.now().toString()
        val entity = AttendanceEntity(
            attendanceId = java.util.UUID.randomUUID().toString(),
            employeeId = employeeId,
            date = today,
            clockInTime = timestamp,
            clockOutTime = null,
            status = AttendanceStatus.PRESENT.name,
            totalHours = 0.0,
            overTimeHours = 0.0
        )
        attendanceDao.insertAttendance(entity)
        return Result.success(entity.toDomain())
    }

    override suspend fun clockOut(employeeId: String, timestamp: Long, location: String): Result<Attendance> {
        // Find the last active clock-in (where clockOutTime is null)
        val existing = attendanceDao.getLastActiveAttendance(employeeId)
            ?: return Result.failure(Exception("No active clock-in record found"))

        val durationMillis = timestamp - existing.clockInTime
        if (durationMillis < 0) {
            return Result.failure(Exception("Clock-out time cannot be before clock-in time"))
        }

        val updated = existing.copy(
            clockOutTime = timestamp,
            totalHours = durationMillis / (1000.0 * 60 * 60)
        )
        attendanceDao.insertAttendance(updated)
        return Result.success(updated.toDomain())
    }

    override suspend fun getAttendanceHistory(
        employeeId: String,
        startDate: Long,
        endDate: Long
    ): Flow<List<Attendance>> {
        return attendanceDao.getAttendanceHistory(employeeId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun syncAttendance() {
        // Trigger WorkManager or sync logic
    }

    private fun AttendanceEntity.toDomain(): Attendance {
        val domainStatus = try {
            AttendanceStatus.valueOf(status)
        } catch (e: IllegalArgumentException) {
            AttendanceStatus.UNKNOWN
        }

        return Attendance(
            attendanceId = attendanceId,
            employeeId = employeeId,
            date = date,
            clockInTime = clockInTime,
            clockOutTime = clockOutTime,
            status = domainStatus,
            totalHours = totalHours,
            overTimeHours = overTimeHours
        )
    }
}
