package com.company.attendanceapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.company.attendanceapp.data.local.database.dao.AttendanceDao
import com.company.attendanceapp.data.local.database.dao.EmployeeDao
import com.company.attendanceapp.data.local.database.dao.LeaveRequestDao
import com.company.attendanceapp.data.local.database.dao.ShiftDao
import com.company.attendanceapp.data.local.database.dao.SyncQueueDao
import com.company.attendanceapp.data.local.database.dao.WorkLocationDao
import com.company.attendanceapp.data.local.database.entities.AttendanceEntity
import com.company.attendanceapp.data.local.database.entities.EmployeeEntity
import com.company.attendanceapp.data.local.database.entities.LeaveRequestEntity
import com.company.attendanceapp.data.local.database.entities.ShiftEntity
import com.company.attendanceapp.data.local.database.entities.SyncQueueEntity
import com.company.attendanceapp.data.local.database.entities.WorkLocationEntity

@Database(
    entities = [
        EmployeeEntity::class,
        AttendanceEntity::class,
        ShiftEntity::class,
        WorkLocationEntity::class,
        LeaveRequestEntity::class,
        SyncQueueEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(com.company.attendanceapp.data.local.database.TypeConverters::class)
abstract class AttendanceDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun shiftDao(): ShiftDao
    abstract fun workLocationDao(): WorkLocationDao
    abstract fun leaveRequestDao(): LeaveRequestDao
    abstract fun syncQueueDao(): SyncQueueDao

    companion object {
        const val DATABASE_NAME = "attendance_db"
    }
}
