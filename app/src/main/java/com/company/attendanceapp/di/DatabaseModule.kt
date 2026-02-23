package com.company.attendanceapp.di

import android.content.Context
import androidx.room.Room
import com.company.attendanceapp.core.common.Constants
import com.company.attendanceapp.data.local.database.AttendanceDatabase
import com.company.attendanceapp.data.local.database.dao.AttendanceDao
import com.company.attendanceapp.data.local.database.dao.EmployeeDao
import com.company.attendanceapp.data.local.database.dao.ShiftDao
import com.company.attendanceapp.data.local.database.dao.SyncQueueDao
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
    fun provideAttendanceDatabase(@ApplicationContext context: Context): AttendanceDatabase {
        return Room.databaseBuilder(
            context,
            AttendanceDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideEmployeeDao(database: AttendanceDatabase): EmployeeDao {
        return database.employeeDao()
    }

    @Provides
    @Singleton
    fun provideAttendanceDao(database: AttendanceDatabase): AttendanceDao {
        return database.attendanceDao()
    }

    @Provides
    @Singleton
    fun provideShiftDao(database: AttendanceDatabase): ShiftDao {
        return database.shiftDao()
    }

    @Provides
    @Singleton
    fun provideSyncQueueDao(database: AttendanceDatabase): SyncQueueDao {
        return database.syncQueueDao()
    }
}
