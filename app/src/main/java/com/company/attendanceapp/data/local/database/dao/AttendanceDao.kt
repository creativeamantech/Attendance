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

    @Query("SELECT * FROM attendance_log WHERE employeeId = :employeeId AND date = :date")
    fun getAttendanceByDate(employeeId: String, date: String): Flow<AttendanceEntity?>

    @Query("SELECT * FROM attendance_log WHERE employeeId = :employeeId")
    fun getAttendanceHistory(employeeId: String): Flow<List<AttendanceEntity>>
}
