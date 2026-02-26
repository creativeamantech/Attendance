package com.company.attendanceapp.core.common

import com.company.attendanceapp.domain.model.Attendance

object ValidationUtils {
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhone(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }

    fun isDateRangeValid(fromDate: String, toDate: String): Boolean {
        // Implement date range validation logic
        return true
    }

    fun isGpsAccuracyAcceptable(accuracy: Float, threshold: Float = 50f): Boolean {
        return accuracy <= threshold
    }

    fun isBuddyPunching(deviceId: String, recentCheckIns: List<Attendance>): Boolean {
        // Check for buddy punching logic
        return false
    }
}
