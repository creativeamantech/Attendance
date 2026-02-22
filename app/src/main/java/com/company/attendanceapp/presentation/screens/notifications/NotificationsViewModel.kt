package com.company.attendanceapp.presentation.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    fun onEvent(event: NotificationEvent) {
        when (event) {
            is NotificationEvent.FilterSelected -> {
                _uiState.update { it.copy(selectedFilter = event.filter) }
            }
            is NotificationEvent.MarkRead -> {
                // TODO
            }
        }
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            // Mock Data
            val items = listOf(
                NotificationItem("1", "Check-in Alert", "You are late by 15 minutes today.", "09:15 AM", NotificationType.ALERT, false),
                NotificationItem("2", "Leave Approved", "Your leave request for Feb 25-27 has been approved.", "Yesterday", NotificationType.APPROVAL, true),
                NotificationItem("3", "Shift Reminder", "Your morning shift starts in 30 minutes.", "08:30 AM", NotificationType.REMINDER, true),
                NotificationItem("4", "System Update", "Attendance data sync completed successfully.", "2 hours ago", NotificationType.SYSTEM, true)
            )
            _uiState.update { it.copy(notifications = items) }
        }
    }
}

data class NotificationsUiState(
    val notifications: List<NotificationItem> = emptyList(),
    val selectedFilter: NotificationFilter = NotificationFilter.ALL
) {
    val filteredNotifications: List<NotificationItem>
        get() = if (selectedFilter == NotificationFilter.ALL) {
            notifications
        } else {
            notifications.filter { it.type.category == selectedFilter }
        }
}

data class NotificationItem(
    val id: String,
    val title: String,
    val body: String,
    val time: String,
    val type: NotificationType,
    val isRead: Boolean
)

enum class NotificationType(val category: NotificationFilter) {
    ALERT(NotificationFilter.ALERTS),
    APPROVAL(NotificationFilter.APPROVALS),
    REMINDER(NotificationFilter.REMINDERS),
    SYSTEM(NotificationFilter.SYSTEM)
}

enum class NotificationFilter {
    ALL, ALERTS, APPROVALS, REMINDERS, SYSTEM
}

sealed class NotificationEvent {
    data class FilterSelected(val filter: NotificationFilter) : NotificationEvent()
    data class MarkRead(val id: String) : NotificationEvent()
}
