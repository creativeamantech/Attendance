package com.company.attendanceapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.company.attendanceapp.data.local.database.entities.LeaveRequestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LeaveRequestDao {
    @Query("SELECT * FROM leave_requests WHERE employeeId = :employeeId ORDER BY appliedAt DESC")
    fun getLeavesByEmployee(employeeId: String): Flow<List<LeaveRequestEntity>>

    @Query("SELECT * FROM leave_requests WHERE status = 'PENDING' ORDER BY appliedAt ASC")
    fun getPendingLeaves(): Flow<List<LeaveRequestEntity>>

    @Query("SELECT * FROM leave_requests WHERE leaveId = :leaveId")
    fun getLeaveById(leaveId: String): Flow<LeaveRequestEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeave(entity: LeaveRequestEntity)

    @Update
    suspend fun updateLeave(entity: LeaveRequestEntity)

    @Query("DELETE FROM leave_requests WHERE leaveId = :leaveId")
    suspend fun deleteLeave(leaveId: String)
}
