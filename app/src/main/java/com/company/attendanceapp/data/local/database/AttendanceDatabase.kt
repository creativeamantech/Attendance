package com.company.attendanceapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.company.attendanceapp.data.local.database.dao.EmployeeDao
import com.company.attendanceapp.data.local.database.entities.EmployeeEntity

@Database(
    entities = [
        EmployeeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AttendanceDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}
