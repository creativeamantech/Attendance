package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey val queueId: String,
    val entityType: String, // ATTENDANCE / LEAVE / EMPLOYEE
    val entityId: String,
    val operation: String, // INSERT / UPDATE / DELETE
    val payload: String, // JSON string
    val retryCount: Int = 0,
    val createdAt: Long,
    val lastAttemptAt: Long?
)
