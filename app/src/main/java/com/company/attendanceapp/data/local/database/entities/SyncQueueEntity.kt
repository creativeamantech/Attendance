package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val entityType: String, // e.g., "ATTENDANCE"
    val operation: String,  // "INSERT", "UPDATE"
    val payload: String,    // JSON
    val createdAt: Long,
    val retryCount: Int = 0
)
