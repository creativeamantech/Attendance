package com.company.attendanceapp.domain.usecase.attendance

import com.company.attendanceapp.domain.model.AttendanceCalculation
import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.domain.model.Shift
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import kotlin.math.max

class ShiftCalculationEngine @Inject constructor() {

    fun calculateAttendance(
        shift: Shift,
        checkInTime: Long,
        checkOutTime: Long
    ): AttendanceCalculation {
        val zoneId = ZoneId.of(shift.timezone)
        val checkInDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(checkInTime), zoneId)
        val checkInDate = checkInDateTime.toLocalDate()

        val shiftStart = checkInDate.atTime(shift.startTimeHour, shift.startTimeMinute)
        var shiftEnd = checkInDate.atTime(shift.endTimeHour, shift.endTimeMinute)

        // Handle night shift
        if (shift.endTimeHour < shift.startTimeHour) {
            shiftEnd = shiftEnd.plusDays(1)
        }

        val gracedShiftStart = shiftStart.plusMinutes(shift.gracePeriodMinutes.toLong())

        val checkInMillis = checkInTime
        val shiftStartMillis = gracedShiftStart.atZone(zoneId).toInstant().toEpochMilli()

        val lateMinutes = max(0L, (checkInMillis - shiftStartMillis) / 60000).toInt()

        val rawWorkedMinutes = (checkOutTime - checkInTime) / 60000
        val netWorkedHours = (rawWorkedMinutes - shift.breakMinutes) / 60.0

        val shiftEndMillis = shiftEnd.atZone(zoneId).toInstant().toEpochMilli()
        val earlyDepartureMinutes = max(0L, (shiftEndMillis - checkOutTime) / 60000).toInt()

        val overtimeHours = max(0.0, netWorkedHours - shift.overtimeThresholdHours)

        val status = when {
            netWorkedHours >= shift.minHours && lateMinutes == 0 -> AttendanceStatus.PRESENT
            netWorkedHours >= shift.minHours && lateMinutes > 0 -> AttendanceStatus.LATE
            netWorkedHours >= shift.minHours / 2 -> AttendanceStatus.HALF_DAY
            else -> AttendanceStatus.INCOMPLETE
        }

        return AttendanceCalculation(
            hoursWorked = netWorkedHours,
            lateMinutes = lateMinutes,
            overtimeHours = overtimeHours,
            earlyDepartureMinutes = earlyDepartureMinutes,
            status = status,
            breakMinutes = shift.breakMinutes
        )
    }
}
