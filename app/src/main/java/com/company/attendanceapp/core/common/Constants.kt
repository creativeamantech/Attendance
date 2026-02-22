package com.company.attendanceapp.core.common

object Constants {
    const val DATABASE_NAME = "attendance_db"
    const val SHARED_PREF_NAME = "attendance_pref"

    // Constants from the prompt
    const val SHEET_RANGE_EMPLOYEES = "employees_master!A:N"
    const val SHEET_RANGE_SHIFTS = "shifts_config!A:N"

    // Error messages
    const val ERROR_UNKNOWN = "An unknown error occurred"
    const val ERROR_NETWORK = "Network error. Please check your connection."

    // Notification Channels
    const val CHANNEL_ALERTS_ID = "attendance_alerts"
    const val CHANNEL_REMINDERS_ID = "shift_reminders"
}
