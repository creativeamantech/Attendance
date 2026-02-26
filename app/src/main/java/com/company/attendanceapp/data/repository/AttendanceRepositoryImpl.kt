package com.company.attendanceapp.data.repository

import com.company.attendanceapp.data.local.database.dao.AttendanceDao
import com.company.attendanceapp.data.local.database.dao.ShiftDao
import com.company.attendanceapp.data.local.database.dao.WorkLocationDao
import com.company.attendanceapp.data.local.mapper.toDomain
import com.company.attendanceapp.data.local.mapper.toEntity
import com.company.attendanceapp.domain.model.Attendance
import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.domain.model.LocationStatus
import com.company.attendanceapp.domain.model.Resource
import com.company.attendanceapp.domain.model.SyncStatus
import com.company.attendanceapp.domain.repository.AttendanceRepository
import com.company.attendanceapp.domain.usecase.attendance.ShiftCalculationEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepositoryImpl @Inject constructor(
    private val attendanceDao: AttendanceDao,
    private val shiftDao: ShiftDao,
    private val workLocationDao: WorkLocationDao,
    private val shiftCalculationEngine: ShiftCalculationEngine
) : AttendanceRepository {

    override fun checkIn(
        employeeId: String,
        latitude: Double,
        longitude: Double,
        accuracy: Float,
        deviceId: String
    ): Flow<Resource<Attendance>> = kotlinx.coroutines.flow.flow {
        emit(Resource.Loading())
        try {
            val activeCheckIn = attendanceDao.getActiveCheckIn(employeeId)
            if (activeCheckIn != null) {
                emit(Resource.Error("Already checked in"))
                return@flow
            }

            // Fetch work location logic (stub for now, assuming valid)
            val locationStatus = LocationStatus.VALID

            val attendance = Attendance(
                logId = java.util.UUID.randomUUID().toString(),
                employeeId = employeeId,
                date = java.time.LocalDate.now().toString(),
                checkInTime = System.currentTimeMillis(),
                checkOutTime = null,
                checkInLatitude = latitude,
                checkInLongitude = longitude,
                checkOutLatitude = null,
                checkOutLongitude = null,
                checkInAccuracy = accuracy,
                locationStatus = locationStatus,
                hoursWorked = 0.0,
                breakMinutes = 0,
                overtimeHours = 0.0,
                lateMinutes = 0,
                earlyDepartureMinutes = 0,
                status = AttendanceStatus.INCOMPLETE,
                deviceId = deviceId,
                ipAddress = null,
                selfieUrl = null,
                notes = null,
                overrideApprovedBy = null,
                syncStatus = SyncStatus.PENDING,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            attendanceDao.insertAttendance(attendance.toEntity())
            emit(Resource.Success(attendance))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override fun checkOut(
        employeeId: String,
        latitude: Double,
        longitude: Double,
        accuracy: Float
    ): Flow<Resource<Attendance>> = kotlinx.coroutines.flow.flow {
        emit(Resource.Loading())
        try {
            val activeCheckIn = attendanceDao.getActiveCheckIn(employeeId)
            if (activeCheckIn == null) {
                emit(Resource.Error("No active check-in found"))
                return@flow
            }

            // Calculate shift logic here using ShiftCalculationEngine
            // Stub for now

            val updatedAttendance = activeCheckIn.copy(
                checkOutTime = System.currentTimeMillis(),
                checkOutLatitude = latitude,
                checkOutLongitude = longitude,
                updatedAt = System.currentTimeMillis(),
                status = AttendanceStatus.PRESENT, // Placeholder
                syncStatus = SyncStatus.PENDING.name
            )

            attendanceDao.updateAttendance(updatedAttendance)
            emit(Resource.Success(updatedAttendance.toDomain()))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override fun getAttendanceByDate(
        employeeId: String,
        date: String
    ): Flow<Resource<Attendance?>> {
        return attendanceDao.getAttendanceByEmployeeAndDate(employeeId, date).map { entity ->
            if (entity != null) {
                Resource.Success(entity.toDomain())
            } else {
                Resource.Success(null)
            }
        }
    }

    override fun getAttendanceHistory(
        employeeId: String,
        fromDate: String,
        toDate: String
    ): Flow<Resource<List<Attendance>>> {
        return attendanceDao.getAttendanceHistory(employeeId, fromDate, toDate).map { entities ->
            Resource.Success(entities.map { it.toDomain() })
        }
    }

    override fun getTodayAttendance(employeeId: String): Flow<Resource<Attendance?>> {
        val today = java.time.LocalDate.now().toString()
        return attendanceDao.getTodayAttendance(employeeId, today).map { entity ->
            if (entity != null) {
                Resource.Success(entity.toDomain())
            } else {
                Resource.Success(null)
            }
        }
    }

    override fun updateAttendance(attendance: Attendance): Flow<Resource<Unit>> = kotlinx.coroutines.flow.flow {
        emit(Resource.Loading())
        try {
            attendanceDao.updateAttendance(attendance.toEntity())
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override fun getPendingSyncQueue(): Flow<List<Attendance>> {
        return attendanceDao.getPendingSyncItems().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun markSynced(logId: String): Flow<Resource<Unit>> = kotlinx.coroutines.flow.flow {
        // Implement logic to mark as synced
    }
}
