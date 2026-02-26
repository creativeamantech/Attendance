package com.company.attendanceapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.company.attendanceapp.data.local.database.entities.SyncQueueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncQueueDao {
    @Query("SELECT * FROM sync_queue ORDER BY createdAt ASC")
    fun getAllPending(): Flow<List<SyncQueueEntity>>

    @Query("SELECT * FROM sync_queue WHERE entityId = :entityId")
    suspend fun getByEntityId(entityId: String): SyncQueueEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToQueue(entity: SyncQueueEntity)

    @Query("UPDATE sync_queue SET retryCount = :retryCount, lastAttemptAt = :lastAttemptAt WHERE queueId = :queueId")
    suspend fun updateRetryCount(queueId: String, retryCount: Int, lastAttemptAt: Long)

    @Query("DELETE FROM sync_queue WHERE queueId = :queueId")
    suspend fun deleteFromQueue(queueId: String)

    @Query("DELETE FROM sync_queue WHERE retryCount >= 3")
    suspend fun deleteAllSynced()
}
