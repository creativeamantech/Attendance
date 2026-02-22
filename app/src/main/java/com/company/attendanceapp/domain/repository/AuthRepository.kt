package com.company.attendanceapp.domain.repository

import com.company.attendanceapp.core.common.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Resource<String>> // Returns user ID on success
    suspend fun logout()
    fun isUserLoggedIn(): Boolean
    fun getCurrentUserId(): String?
}
