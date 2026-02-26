package com.company.attendanceapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.company.attendanceapp.data.local.database.entities.WorkLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkLocationDao {
    @Query("SELECT * FROM work_locations WHERE locationId = :locationId")
    fun getLocationById(locationId: String): Flow<WorkLocationEntity?>

    @Query("SELECT * FROM work_locations")
    fun getAllLocations(): Flow<List<WorkLocationEntity>>

    @Query("SELECT * FROM work_locations WHERE isActive = 1")
    fun getActiveLocations(): Flow<List<WorkLocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(entity: WorkLocationEntity)

    @Update
    suspend fun updateLocation(entity: WorkLocationEntity)
}
