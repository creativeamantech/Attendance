package com.company.attendanceapp.core.common

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateTimeUtils {
    fun todayAsIsoString(timezone: String = "UTC"): String {
        return LocalDate.now(ZoneId.of(timezone)).toString()
    }

    fun epochToLocalTime(epochMillis: Long, timezone: String): String {
        val zoneId = ZoneId.of(timezone)
        val time = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), zoneId)
        return time.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    fun epochToDisplayDate(epochMillis: Long): String {
        val zoneId = ZoneId.systemDefault()
        val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), zoneId)
        return date.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy"))
    }

    fun isoToEpoch(isoDate: String): Long {
        return LocalDate.parse(isoDate).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun minutesToHoursMinutes(minutes: Int): String {
        val hours = minutes / 60
        val mins = minutes % 60
        return "${hours}h ${mins}m"
    }

    fun getStartOfDayEpoch(date: String, timezone: String): Long {
        return LocalDate.parse(date).atStartOfDay(ZoneId.of(timezone)).toInstant().toEpochMilli()
    }

    fun getEndOfDayEpoch(date: String, timezone: String): Long {
        return LocalDate.parse(date).atTime(23, 59, 59).atZone(ZoneId.of(timezone)).toInstant().toEpochMilli()
    }
}
