package com.company.attendanceapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.company.attendanceapp.data.local.database.entities.AttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity)

    @Query("SELECT * FROM attendance WHERE employeeId = :employeeId AND date = :date")
    suspend fun getAttendanceByDate(employeeId: String, date: String): AttendanceEntity?

    @Query("SELECT * FROM attendance WHERE employeeId = :employeeId ORDER BY date DESC")
    fun getAttendanceHistory(employeeId: String): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE employeeId = :employeeId AND clockOutTime IS NULL ORDER BY clockInTime DESC LIMIT 1")
    suspend fun getLastActiveAttendance(employeeId: String): AttendanceEntity?

    @Query("SELECT * FROM attendance WHERE clockOutTime IS NULL")
    suspend fun getActiveAttendances(): List<AttendanceEntity>
}
