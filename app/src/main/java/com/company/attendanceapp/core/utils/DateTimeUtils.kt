package com.company.attendanceapp.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {

    private val TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a")
    private val DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")
    private val SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d")

    fun formatTime(dateTime: LocalDateTime?): String {
        return dateTime?.format(TIME_FORMATTER) ?: "--:--"
    }

    fun formatDate(date: LocalDate?): String {
        return date?.format(DATE_FORMATTER) ?: ""
    }

    fun formatShortDate(date: LocalDate?): String {
        return date?.format(SHORT_DATE_FORMATTER) ?: ""
    }
}
