package com.company.attendanceapp.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.attendanceapp.domain.model.Employee
import com.company.attendanceapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // Mock profile load
            val mockEmployee = Employee(
                employeeId = "EMP001",
                name = "Riya Sharma",
                email = "riya.sharma@company.com",
                phone = "+91 98765 43210",
                department = "Engineering - Mobile",
                role = "Senior Developer",
                managerId = "EMP005",
                shiftId = "SHIFT_MORNING",
                locationId = "LOC_BLR",
                remoteAllowed = true,
                probationFlag = false,
                status = "ACTIVE",
                profilePhotoUrl = null,
                createdAt = System.currentTimeMillis()
            )
            _uiState.update { it.copy(isLoading = false, employee = mockEmployee) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            // Navigation to login handled by MainActivity/AppNavGraph observing auth state
            // For now, trigger a state flag
            _uiState.update { it.copy(isLoggedOut = true) }
        }
    }
}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val employee: Employee? = null,
    val isLoggedOut: Boolean = false
)
