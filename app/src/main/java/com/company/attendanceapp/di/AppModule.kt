package com.company.attendanceapp.di

import android.app.Application
import android.content.Context
import com.company.attendanceapp.AttendanceApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(@dagger.hilt.android.qualifiers.ApplicationContext app: Context): AttendanceApplication {
        return app as AttendanceApplication
    }
}
