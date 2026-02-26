package com.company.attendanceapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.company.attendanceapp.data.local.database.entities.AttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Query("SELECT * FROM attendance_log WHERE employeeId = :employeeId AND date = :date")
    fun getAttendanceByEmployeeAndDate(employeeId: String, date: String): Flow<AttendanceEntity?>

    @Query("SELECT * FROM attendance_log WHERE employeeId = :employeeId AND date BETWEEN :fromDate AND :toDate ORDER BY date DESC")
    fun getAttendanceHistory(employeeId: String, fromDate: String, toDate: String): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance_log WHERE employeeId = :employeeId AND date = :today")
    fun getTodayAttendance(employeeId: String, today: String): Flow<AttendanceEntity?>

    @Query("SELECT * FROM attendance_log WHERE syncStatus = 'PENDING'")
    fun getPendingSyncItems(): Flow<List<AttendanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(entity: AttendanceEntity)

    @Update
    suspend fun updateAttendance(entity: AttendanceEntity)

    @Query("DELETE FROM attendance_log WHERE logId = :logId")
    suspend fun deleteAttendance(logId: String)

    @Query("SELECT * FROM attendance_log WHERE employeeId = :employeeId AND checkOutTime IS NULL")
    suspend fun getActiveCheckIn(employeeId: String): AttendanceEntity?
}
