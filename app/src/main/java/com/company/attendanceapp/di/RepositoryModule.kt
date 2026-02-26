package com.company.attendanceapp.di

import com.company.attendanceapp.data.repository.AttendanceRepositoryImpl
import com.company.attendanceapp.data.repository.EmployeeRepositoryImpl
import com.company.attendanceapp.data.repository.LeaveRepositoryImpl
import com.company.attendanceapp.data.repository.LocationRepositoryImpl
import com.company.attendanceapp.data.repository.ShiftRepositoryImpl
import com.company.attendanceapp.domain.repository.AttendanceRepository
import com.company.attendanceapp.domain.repository.EmployeeRepository
import com.company.attendanceapp.domain.repository.LeaveRepository
import com.company.attendanceapp.domain.repository.LocationRepository
import com.company.attendanceapp.domain.repository.ShiftRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAttendanceRepository(
        attendanceRepositoryImpl: AttendanceRepositoryImpl
    ): AttendanceRepository

    @Binds
    @Singleton
    abstract fun bindEmployeeRepository(
        employeeRepositoryImpl: EmployeeRepositoryImpl
    ): EmployeeRepository

    @Binds
    @Singleton
    abstract fun bindShiftRepository(
        shiftRepositoryImpl: ShiftRepositoryImpl
    ): ShiftRepository

    @Binds
    @Singleton
    abstract fun bindLeaveRepository(
        leaveRepositoryImpl: LeaveRepositoryImpl
    ): LeaveRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository
}
