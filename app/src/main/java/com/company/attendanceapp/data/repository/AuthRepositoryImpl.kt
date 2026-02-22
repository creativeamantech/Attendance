package com.company.attendanceapp.data.repository

import com.company.attendanceapp.core.common.Resource
import com.company.attendanceapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override fun login(email: String, password: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        delay(1000) // Simulate network delay

        // Mock implementation for development without real Firebase project
        if (email.contains("error")) {
            emit(Resource.Error("Invalid credentials"))
        } else {
            // In a real app:
            // try {
            //     val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            //     emit(Resource.Success(result.user?.uid ?: ""))
            // } catch (e: Exception) {
            //     emit(Resource.Error(e.message ?: "Unknown error"))
            // }
            emit(Resource.Success("mock-user-id"))
        }
    }

    override suspend fun logout() {
        // firebaseAuth.signOut()
        delay(500)
    }

    override fun isUserLoggedIn(): Boolean {
        // return firebaseAuth.currentUser != null
        return false // Always return false to show login screen initially
    }

    override fun getCurrentUserId(): String? {
        // return firebaseAuth.currentUser?.uid
        return "mock-user-id"
    }
}
