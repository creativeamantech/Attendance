package com.company.attendanceapp.data.local.mapper

import com.company.attendanceapp.data.local.database.TypeConverters
import com.company.attendanceapp.data.local.database.entities.ShiftEntity
import com.company.attendanceapp.domain.model.Shift

fun ShiftEntity.toDomain(): Shift {
    val converters = TypeConverters()
    return Shift(
        shiftId = shiftId,
        shiftName = shiftName,
        shiftType = shiftType,
        startTimeHour = startTimeHour,
        startTimeMinute = startTimeMinute,
        endTimeHour = endTimeHour,
        endTimeMinute = endTimeMinute,
        breakMinutes = breakMinutes,
        workingDays = converters.toDayOfWeekList(workingDays),
        gracePeriodMinutes = gracePeriodMinutes,
        minHours = minHours,
        overtimeThresholdHours = overtimeThresholdHours,
        isNightShift = isNightShift,
        timezone = timezone
    )
}

fun Shift.toEntity(): ShiftEntity {
    val converters = TypeConverters()
    return ShiftEntity(
        shiftId = shiftId,
        shiftName = shiftName,
        shiftType = shiftType,
        startTimeHour = startTimeHour,
        startTimeMinute = startTimeMinute,
        endTimeHour = endTimeHour,
        endTimeMinute = endTimeMinute,
        breakMinutes = breakMinutes,
        workingDays = converters.fromDayOfWeekList(workingDays),
        gracePeriodMinutes = gracePeriodMinutes,
        minHours = minHours,
        overtimeThresholdHours = overtimeThresholdHours,
        isNightShift = isNightShift,
        timezone = timezone
    )
}
