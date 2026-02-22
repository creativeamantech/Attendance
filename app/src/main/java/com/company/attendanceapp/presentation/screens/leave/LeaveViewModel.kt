package com.company.attendanceapp.presentation.screens.leave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class LeaveViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LeaveUiState())
    val uiState: StateFlow<LeaveUiState> = _uiState.asStateFlow()

    init {
        loadLeaveData()
    }

    fun onEvent(event: LeaveEvent) {
        when (event) {
            is LeaveEvent.SelectTab -> {
                _uiState.update { it.copy(selectedTabIndex = event.index) }
            }
            is LeaveEvent.LeaveTypeChanged -> {
                _uiState.update { it.copy(applyLeaveType = event.type) }
            }
            is LeaveEvent.FromDateChanged -> {
                _uiState.update { it.copy(applyFromDate = event.date) }
            }
            is LeaveEvent.ToDateChanged -> {
                _uiState.update { it.copy(applyToDate = event.date) }
            }
            is LeaveEvent.ReasonChanged -> {
                _uiState.update { it.copy(applyReason = event.reason) }
            }
            is LeaveEvent.SubmitLeaveRequest -> {
                submitLeave()
            }
        }
    }

    private fun loadLeaveData() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            // Mock Data
            val requests = listOf(
                LeaveRequest(
                    id = "L001",
                    type = "Annual Leave",
                    fromDate = LocalDate.now().plusDays(5),
                    toDate = LocalDate.now().plusDays(7),
                    status = "Pending",
                    reason = "Family function"
                ),
                LeaveRequest(
                    id = "L002",
                    type = "Sick Leave",
                    fromDate = LocalDate.now().minusDays(10),
                    toDate = LocalDate.now().minusDays(9),
                    status = "Approved",
                    reason = "Viral Fever"
                )
            )

            val balances = listOf(
                LeaveBalance("Annual Leave", 12, 6),
                LeaveBalance("Sick Leave", 10, 2),
                LeaveBalance("Casual Leave", 8, 8)
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    leaveRequests = requests,
                    leaveBalances = balances
                )
            }
        }
    }

    private fun submitLeave() {
        // Mock submission
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            // Simulate API call
            kotlinx.coroutines.delay(1000)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    selectedTabIndex = 0, // Switch to My Leaves
                    // Reset form
                    applyLeaveType = "Annual Leave",
                    applyFromDate = null,
                    applyToDate = null,
                    applyReason = ""
                )
            }
        }
    }
}

data class LeaveUiState(
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val leaveRequests: List<LeaveRequest> = emptyList(),
    val leaveBalances: List<LeaveBalance> = emptyList(),
    // Apply Form State
    val applyLeaveType: String = "Annual Leave",
    val applyFromDate: LocalDate? = null,
    val applyToDate: LocalDate? = null,
    val applyReason: String = ""
)

data class LeaveRequest(
    val id: String,
    val type: String,
    val fromDate: LocalDate,
    val toDate: LocalDate,
    val status: String,
    val reason: String
)

data class LeaveBalance(
    val type: String,
    val total: Int,
    val used: Int
) {
    val remaining: Int get() = total - used
}

sealed class LeaveEvent {
    data class SelectTab(val index: Int) : LeaveEvent()
    data class LeaveTypeChanged(val type: String) : LeaveEvent()
    data class FromDateChanged(val date: LocalDate) : LeaveEvent()
    data class ToDateChanged(val date: LocalDate) : LeaveEvent()
    data class ReasonChanged(val reason: String) : LeaveEvent()
    object SubmitLeaveRequest : LeaveEvent()
}
