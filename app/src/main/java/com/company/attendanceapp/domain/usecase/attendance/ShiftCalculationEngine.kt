package com.company.attendanceapp.domain.usecase.attendance

import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.domain.model.Shift
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class ShiftCalculationEngine @Inject constructor() {

    fun calculateAttendance(
        shift: Shift,
        checkInTime: LocalDateTime,
        checkOutTime: LocalDateTime?
    ): AttendanceCalculation {
        val shiftStart = shift.startTime.atDate(checkInTime.toLocalDate())
        val shiftEnd = shift.endTime.atDate(checkInTime.toLocalDate())
            .let { if (shift.isNightShift && shift.endTime < shift.startTime)
                   it.plusDays(1) else it }

        val lateMinutes = maxOf(0L,
            ChronoUnit.MINUTES.between(
                shiftStart.plusMinutes(shift.gracePeriodMinutes.toLong()),
                checkInTime
            )
        )

        val hoursWorked = checkOutTime?.let {
            val totalMinutes = ChronoUnit.MINUTES.between(checkInTime, it)
            // Subtract break only if worked enough? Prompt says simple subtraction.
            // "ChronoUnit.MINUTES.between(checkInTime, it).toDouble() / 60 - (shift.breakMinutes.toDouble() / 60)"
            (totalMinutes.toDouble() / 60) - (shift.breakMinutes.toDouble() / 60)
        } ?: 0.0

        val adjustedHoursWorked = maxOf(0.0, hoursWorked)

        val overtimeHours = maxOf(0.0, adjustedHoursWorked - shift.overtimeThresholdHours)

        val earlyDepartureMinutes = checkOutTime?.let {
            maxOf(0L, ChronoUnit.MINUTES.between(it, shiftEnd))
        } ?: 0L

        val status = when {
            checkOutTime == null -> AttendanceStatus.INCOMPLETE
            adjustedHoursWorked >= shift.minHours / 2 && adjustedHoursWorked < shift.minHours -> AttendanceStatus.HALF_DAY
            lateMinutes > 0 -> AttendanceStatus.LATE
            else -> AttendanceStatus.PRESENT
        }

        return AttendanceCalculation(
            hoursWorked = adjustedHoursWorked,
            lateMinutes = lateMinutes.toInt(),
            overtimeHours = overtimeHours,
            earlyDepartureMinutes = earlyDepartureMinutes.toInt(),
            status = status
        )
    }
}

data class AttendanceCalculation(
    val hoursWorked: Double,
    val lateMinutes: Int,
    val overtimeHours: Double,
    val earlyDepartureMinutes: Int,
    val status: AttendanceStatus
)
