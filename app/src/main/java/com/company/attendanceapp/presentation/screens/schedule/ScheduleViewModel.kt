package com.company.attendanceapp.presentation.screens.schedule

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
import java.time.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    init {
        loadSchedule(LocalDate.now())
    }

    fun onEvent(event: ScheduleEvent) {
        when (event) {
            is ScheduleEvent.PreviousWeek -> {
                val newDate = _uiState.value.selectedDate.minusWeeks(1)
                loadSchedule(newDate)
            }
            is ScheduleEvent.NextWeek -> {
                val newDate = _uiState.value.selectedDate.plusWeeks(1)
                loadSchedule(newDate)
            }
            is ScheduleEvent.SelectDay -> {
                _uiState.update { it.copy(selectedDate = event.date) }
            }
            is ScheduleEvent.SelectShift -> {
                _uiState.update { it.copy(selectedShift = event.shift) }
            }
            is ScheduleEvent.ClearSelection -> {
                _uiState.update { it.copy(selectedShift = null) }
            }
        }
    }

    private fun loadSchedule(date: LocalDate) {
        _uiState.update { it.copy(isLoading = true, selectedDate = date) }

        // Calculate start and end of week based on selected date
        val weekFields = WeekFields.of(Locale.getDefault())
        val startOfWeek = date.with(weekFields.dayOfWeek(), 1L)
        val endOfWeek = date.with(weekFields.dayOfWeek(), 7L)

        // Mock Data
        viewModelScope.launch {
            val shifts = (0..6).map { offset ->
                val day = startOfWeek.plusDays(offset.toLong())
                val isWeekend = day.dayOfWeek.value >= 6

                ShiftItem(
                    date = day,
                    isWorkingDay = !isWeekend,
                    shiftName = if (isWeekend) "Weekend Off" else "Morning Shift - A",
                    startTime = if (isWeekend) null else "09:00",
                    endTime = if (isWeekend) null else "18:00",
                    status = if (isWeekend) "OFF" else if (day.isAfter(LocalDate.now())) "UPCOMING" else "COMPLETED",
                    hoursWorked = if (!isWeekend && day.isBefore(LocalDate.now())) 9.0 else 0.0,
                    breakMinutes = 60
                )
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    weekStartDate = startOfWeek,
                    weekEndDate = endOfWeek,
                    shifts = shifts
                )
            }
        }
    }
}

data class ScheduleUiState(
    val isLoading: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now(),
    val weekStartDate: LocalDate = LocalDate.now(),
    val weekEndDate: LocalDate = LocalDate.now(),
    val shifts: List<ShiftItem> = emptyList(),
    val selectedShift: ShiftItem? = null
)

data class ShiftItem(
    val date: LocalDate,
    val isWorkingDay: Boolean,
    val shiftName: String,
    val startTime: String?,
    val endTime: String?,
    val status: String,
    val hoursWorked: Double,
    val breakMinutes: Int
)

sealed class ScheduleEvent {
    object PreviousWeek : ScheduleEvent()
    object NextWeek : ScheduleEvent()
    data class SelectDay(val date: LocalDate) : ScheduleEvent()
    data class SelectShift(val shift: ShiftItem) : ScheduleEvent()
    object ClearSelection : ScheduleEvent()
}
