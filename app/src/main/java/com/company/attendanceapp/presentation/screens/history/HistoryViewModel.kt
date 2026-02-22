package com.company.attendanceapp.presentation.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.attendanceapp.core.common.Resource
import com.company.attendanceapp.domain.model.Attendance
import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.domain.repository.AttendanceRepository
import com.company.attendanceapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory(YearMonth.now())
    }

    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.PreviousMonth -> {
                val newMonth = _uiState.value.selectedMonth.minusMonths(1)
                loadHistory(newMonth)
            }
            is HistoryEvent.NextMonth -> {
                val newMonth = _uiState.value.selectedMonth.plusMonths(1)
                loadHistory(newMonth)
            }
            is HistoryEvent.ToggleViewMode -> {
                _uiState.update { it.copy(isCalendarView = !it.isCalendarView) }
            }
            is HistoryEvent.DateSelected -> {
                selectDate(event.date)
            }
        }
    }

    private fun loadHistory(month: YearMonth) {
        _uiState.update { it.copy(selectedMonth = month, isLoading = true) }
        val userId = authRepository.getCurrentUserId() ?: return

        val fromDate = month.atDay(1)
        val toDate = month.atEndOfMonth()

        viewModelScope.launch {
            attendanceRepository.getAttendanceHistory(userId, fromDate, toDate)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            val attendanceList = result.data ?: emptyList()
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    attendanceList = attendanceList,
                                    presentDays = attendanceList.count { a -> a.status == AttendanceStatus.PRESENT },
                                    lateDays = attendanceList.count { a -> a.status == AttendanceStatus.LATE },
                                    absentDays = attendanceList.count { a -> a.status == AttendanceStatus.ABSENT },
                                    leaveDays = attendanceList.count { a -> a.status == AttendanceStatus.ON_LEAVE }
                                )
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update { it.copy(isLoading = false, error = result.message) }
                        }
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                    }
                }
        }
    }

    private fun selectDate(date: LocalDate) {
        val attendance = _uiState.value.attendanceList.find { it.date.toLocalDate() == date }
        _uiState.update { it.copy(selectedDate = date, selectedDateAttendance = attendance) }
    }
}

data class HistoryUiState(
    val selectedMonth: YearMonth = YearMonth.now(),
    val isCalendarView: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null,
    val attendanceList: List<Attendance> = emptyList(),
    val selectedDate: LocalDate? = null,
    val selectedDateAttendance: Attendance? = null,
    // Summary Stats
    val presentDays: Int = 0,
    val lateDays: Int = 0,
    val absentDays: Int = 0,
    val leaveDays: Int = 0
)

sealed class HistoryEvent {
    object PreviousMonth : HistoryEvent()
    object NextMonth : HistoryEvent()
    object ToggleViewMode : HistoryEvent()
    data class DateSelected(val date: LocalDate) : HistoryEvent()
}
