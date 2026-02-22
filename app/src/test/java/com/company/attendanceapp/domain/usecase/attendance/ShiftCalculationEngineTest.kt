package com.company.attendanceapp.domain.usecase.attendance

import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.domain.model.Shift
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.LocalTime

class ShiftCalculationEngineTest {

    private val engine = ShiftCalculationEngine()

    private val morningShift = Shift(
        shiftId = "S1",
        shiftName = "Morning",
        startTime = LocalTime.of(9, 0),
        endTime = LocalTime.of(18, 0),
        breakMinutes = 60,
        gracePeriodMinutes = 10,
        minHours = 8.0,
        overtimeThresholdHours = 9.0,
        isNightShift = false,
        flexibleFlag = false
    )

    @Test
    fun `check-in on time should return PRESENT`() {
        val checkIn = LocalDateTime.of(2023, 10, 25, 9, 5)
        val checkOut = LocalDateTime.of(2023, 10, 25, 18, 5)

        val result = engine.calculateAttendance(morningShift, checkIn, checkOut)

        assertEquals(AttendanceStatus.PRESENT, result.status)
        assertEquals(0, result.lateMinutes)
    }

    @Test
    fun `check-in 12 minutes late should return LATE status with lateMinutes = 12`() {
        // Late by 12 mins (grace is 10)
        val checkIn = LocalDateTime.of(2023, 10, 25, 9, 12)
        val checkOut = LocalDateTime.of(2023, 10, 25, 18, 30)

        val result = engine.calculateAttendance(morningShift, checkIn, checkOut)

        assertEquals(AttendanceStatus.LATE, result.status)
        assertEquals(2, result.lateMinutes) // 12 - 10 = 2
    }

    @Test
    fun `working less than min hours should return HALF_DAY`() {
        // Worked 4 hours (min is 8, half is 4)
        val checkIn = LocalDateTime.of(2023, 10, 25, 9, 0)
        val checkOut = LocalDateTime.of(2023, 10, 25, 14, 0) // 5 hours - 1 hour break = 4 hours work

        val result = engine.calculateAttendance(morningShift, checkIn, checkOut)

        assertEquals(AttendanceStatus.HALF_DAY, result.status)
    }
}
