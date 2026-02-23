package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val action: String, // INSERT, UPDATE
    val tableName: String, // ATTENDANCE, LEAVE
    val dataPayload: String, // JSON
    val createdAt: Long,
    val retryCount: Int = 0
)
