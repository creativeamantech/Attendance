package com.company.attendanceapp.presentation.screens.checkin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.attendanceapp.core.common.Resource
import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.domain.repository.AttendanceRepository
import com.company.attendanceapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckInUiState())
    val uiState: StateFlow<CheckInUiState> = _uiState.asStateFlow()

    init {
        // Start location updates (mock)
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isInsideZone = true, // Mock: Inside geofence
                    currentLocationName = "Tech Park, Bangalore",
                    distanceFromZone = 0.0
                )
            }
        }
    }

    fun onEvent(event: CheckInEvent) {
        when (event) {
            is CheckInEvent.CheckInClicked -> {
                performCheckIn()
            }
            is CheckInEvent.CheckOutClicked -> {
                performCheckOut()
            }
            is CheckInEvent.RefreshLocation -> {
                // TODO: Refresh GPS
            }
        }
    }

    private fun performCheckIn() {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = attendanceRepository.checkIn(
                userId,
                12.9716, 77.5946, 12f, null
            )
            when (result) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isCheckedIn = true,
                            checkInSuccess = true,
                            checkInTime = result.data?.checkInTime.toString() // simplified
                        )
                    }
                    delay(2000) // Show success for 2s
                    _uiState.update { it.copy(checkInSuccess = false) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                else -> {}
            }
        }
    }

    private fun performCheckOut() {
        // Similar to CheckIn
    }
}

data class CheckInUiState(
    val isLoading: Boolean = false,
    val isCheckedIn: Boolean = false,
    val isInsideZone: Boolean = false,
    val currentLocationName: String = "--",
    val distanceFromZone: Double = 0.0,
    val error: String? = null,
    val checkInSuccess: Boolean = false,
    val checkInTime: String? = null
)

sealed class CheckInEvent {
    object CheckInClicked : CheckInEvent()
    object CheckOutClicked : CheckInEvent()
    object RefreshLocation : CheckInEvent()
}
