package com.company.attendanceapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.attendanceapp.core.common.Resource
import com.company.attendanceapp.domain.model.Attendance
import com.company.attendanceapp.domain.repository.AttendanceRepository
import com.company.attendanceapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        val userId = authRepository.getCurrentUserId() ?: return

        // Mock user profile loading
        _uiState.update {
            it.copy(
                userName = "Riya", // TODO: Fetch real user profile
                currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy"))
            )
        }

        // Load today's attendance
        viewModelScope.launch {
            attendanceRepository.getAttendanceForDate(userId, LocalDate.now())
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    todayAttendance = result.data,
                                    isCheckedIn = result.data?.checkInTime != null && result.data.checkOutTime == null
                                )
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update { it.copy(isLoading = false, error = result.message) }
                        }
                    }
                }
        }
    }
}

data class HomeUiState(
    val userName: String = "",
    val currentDate: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val todayAttendance: Attendance? = null,
    val isCheckedIn: Boolean = false,
    val unreadNotifications: Int = 3 // Mock count
)
