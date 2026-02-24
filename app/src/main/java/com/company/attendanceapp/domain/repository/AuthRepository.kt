package com.company.attendanceapp.domain.repository

import com.company.attendanceapp.domain.model.Employee

interface AuthRepository {
    suspend fun login(email: String, pin: String): Result<Employee>
    suspend fun logout()
    suspend fun getCurrentUser(): Employee?
    suspend fun isLoggedIn(): Boolean
}
