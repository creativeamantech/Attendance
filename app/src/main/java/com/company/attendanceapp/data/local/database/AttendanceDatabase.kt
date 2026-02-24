package com.company.attendanceapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.company.attendanceapp.data.local.database.dao.AttendanceDao
import com.company.attendanceapp.data.local.database.dao.EmployeeDao
import com.company.attendanceapp.data.local.database.dao.ShiftDao
import com.company.attendanceapp.data.local.database.dao.SyncQueueDao
import com.company.attendanceapp.data.local.database.entities.AttendanceEntity
import com.company.attendanceapp.data.local.database.entities.EmployeeEntity
import com.company.attendanceapp.data.local.database.entities.ShiftEntity
import com.company.attendanceapp.data.local.database.entities.SyncQueueEntity

@Database(
    entities = [
        EmployeeEntity::class,
        AttendanceEntity::class,
        ShiftEntity::class,
        SyncQueueEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AttendanceDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun shiftDao(): ShiftDao
    abstract fun syncQueueDao(): SyncQueueDao
}
