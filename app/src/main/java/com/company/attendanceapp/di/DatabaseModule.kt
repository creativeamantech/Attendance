package com.company.attendanceapp.di

import android.content.Context
import androidx.room.Room
import com.company.attendanceapp.data.local.database.AttendanceDatabase
import com.company.attendanceapp.data.local.database.dao.AttendanceDao
import com.company.attendanceapp.data.local.database.dao.EmployeeDao
import com.company.attendanceapp.data.local.database.dao.LeaveRequestDao
import com.company.attendanceapp.data.local.database.dao.ShiftDao
import com.company.attendanceapp.data.local.database.dao.SyncQueueDao
import com.company.attendanceapp.data.local.database.dao.WorkLocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AttendanceDatabase {
        return Room.databaseBuilder(
            context,
            AttendanceDatabase::class.java,
            AttendanceDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration() // Handle migrations safely for dev
        .build()
    }

    @Provides
    fun provideEmployeeDao(database: AttendanceDatabase): EmployeeDao {
        return database.employeeDao()
    }

    @Provides
    fun provideAttendanceDao(database: AttendanceDatabase): AttendanceDao {
        return database.attendanceDao()
    }

    @Provides
    fun provideShiftDao(database: AttendanceDatabase): ShiftDao {
        return database.shiftDao()
    }

    @Provides
    fun provideWorkLocationDao(database: AttendanceDatabase): WorkLocationDao {
        return database.workLocationDao()
    }

    @Provides
    fun provideLeaveRequestDao(database: AttendanceDatabase): LeaveRequestDao {
        return database.leaveRequestDao()
    }

    @Provides
    fun provideSyncQueueDao(database: AttendanceDatabase): SyncQueueDao {
        return database.syncQueueDao()
    }
}
