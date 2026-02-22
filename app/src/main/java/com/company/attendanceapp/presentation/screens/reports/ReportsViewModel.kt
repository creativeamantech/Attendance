package com.company.attendanceapp.presentation.screens.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.attendanceapp.domain.repository.AttendanceRepository
import com.company.attendanceapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        loadReports(YearMonth.now())
    }

    private fun loadReports(month: YearMonth) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, selectedMonth = month) }

            // Mock Data for Charts
            _uiState.update {
                it.copy(
                    isLoading = false,
                    totalWorkingDays = 22,
                    presentDays = 19,
                    lateArrivals = 3,
                    overtimeHours = 7.5,
                    // Mock Weekly Data for Bar Chart: Mon-Fri
                    weeklyHours = listOf(8.5f, 9.0f, 8.0f, 9.5f, 8.0f, 0f, 0f),
                    // Mock Trend Data for Line Chart (first 10 days)
                    attendanceTrend = listOf(8f, 8.5f, 9f, 8f, 7.5f, 9f, 8f, 8f, 9.5f, 8f)
                )
            }
        }
    }
}

data class ReportsUiState(
    val selectedMonth: YearMonth = YearMonth.now(),
    val isLoading: Boolean = false,
    val totalWorkingDays: Int = 0,
    val presentDays: Int = 0,
    val lateArrivals: Int = 0,
    val overtimeHours: Double = 0.0,
    val weeklyHours: List<Float> = emptyList(),
    val attendanceTrend: List<Float> = emptyList()
)
