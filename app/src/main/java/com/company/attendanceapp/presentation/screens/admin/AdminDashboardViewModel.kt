package com.company.attendanceapp.presentation.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.domain.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AdminDashboardUiState())
    val uiState: StateFlow<AdminDashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            // Mock Data
            val stats = AdminStats(
                totalEmployees = 45,
                presentToday = 38,
                absent = 2,
                onLeave = 3,
                lateArrivals = 5,
                outOfZone = 1,
                overtimeRunning = 8,
                pendingApprovals = 4
            )

            val liveFeed = listOf(
                LiveAttendanceItem("E001", "Riya Sharma", AttendanceStatus.PRESENT, "09:02 AM", "Tech Park HQ"),
                LiveAttendanceItem("E004", "Arjun Mehta", AttendanceStatus.LATE, "09:45 AM", "Client Site A"),
                LiveAttendanceItem("E007", "Sneha Patil", AttendanceStatus.PRESENT, "08:55 AM", "Remote"),
                LiveAttendanceItem("E012", "Rohan Das", AttendanceStatus.OUT_OF_ZONE, "09:10 AM", "Unknown Location")
            )

            val employees = listOf(
                Employee("E001", "Riya Sharma", "riya@co.com", "", "Eng", "Dev", null, null, null, true, false, "ACTIVE", null, 0),
                Employee("E004", "Arjun Mehta", "arjun@co.com", "", "Sales", "Manager", null, null, null, false, false, "ACTIVE", null, 0)
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    stats = stats,
                    liveFeed = liveFeed,
                    employees = employees
                )
            }
        }
    }
}

data class AdminDashboardUiState(
    val isLoading: Boolean = false,
    val stats: AdminStats = AdminStats(),
    val liveFeed: List<LiveAttendanceItem> = emptyList(),
    val employees: List<Employee> = emptyList(),
    val selectedTab: Int = 0 // 0: Dashboard, 1: Employees, 2: Map
)

data class AdminStats(
    val totalEmployees: Int = 0,
    val presentToday: Int = 0,
    val absent: Int = 0,
    val onLeave: Int = 0,
    val lateArrivals: Int = 0,
    val outOfZone: Int = 0,
    val overtimeRunning: Int = 0,
    val pendingApprovals: Int = 0
)

data class LiveAttendanceItem(
    val employeeId: String,
    val name: String,
    val status: AttendanceStatus,
    val time: String,
    val location: String
)
