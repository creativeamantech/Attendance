package com.company.attendanceapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.company.attendanceapp.data.local.database.entities.ShiftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftDao {
    @Query("SELECT * FROM shifts WHERE shiftId = :shiftId")
    fun getShiftById(shiftId: String): Flow<ShiftEntity?>

    @Query("SELECT * FROM shifts")
    fun getAllShifts(): Flow<List<ShiftEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShift(entity: ShiftEntity)

    @Update
    suspend fun updateShift(entity: ShiftEntity)

    @Query("DELETE FROM shifts WHERE shiftId = :shiftId")
    suspend fun deleteShift(shiftId: String)
}
