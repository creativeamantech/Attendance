package com.company.attendanceapp.data.local.database

import androidx.room.TypeConverter
import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.domain.model.EmployeeStatus
import com.company.attendanceapp.domain.model.HalfDayPeriod
import com.company.attendanceapp.domain.model.LeaveStatus
import com.company.attendanceapp.domain.model.LeaveType
import com.company.attendanceapp.domain.model.LocationStatus
import com.company.attendanceapp.domain.model.LocationType
import com.company.attendanceapp.domain.model.ShiftType
import java.time.DayOfWeek

class TypeConverters {

    @TypeConverter
    fun fromEmployeeStatus(status: EmployeeStatus): String = status.name

    @TypeConverter
    fun toEmployeeStatus(status: String): EmployeeStatus = EmployeeStatus.valueOf(status)

    @TypeConverter
    fun fromShiftType(type: ShiftType): String = type.name

    @TypeConverter
    fun toShiftType(type: String): ShiftType = ShiftType.valueOf(type)

    @TypeConverter
    fun fromLocationType(type: LocationType): String = type.name

    @TypeConverter
    fun toLocationType(type: String): LocationType = LocationType.valueOf(type)

    @TypeConverter
    fun fromLocationStatus(status: LocationStatus): String = status.name

    @TypeConverter
    fun toLocationStatus(status: String): LocationStatus = LocationStatus.valueOf(status)

    @TypeConverter
    fun fromAttendanceStatus(status: AttendanceStatus): String = status.name

    @TypeConverter
    fun toAttendanceStatus(status: String): AttendanceStatus = AttendanceStatus.valueOf(status)

    @TypeConverter
    fun fromLeaveType(type: LeaveType): String = type.name

    @TypeConverter
    fun toLeaveType(type: String): LeaveType = LeaveType.valueOf(type)

    @TypeConverter
    fun fromHalfDayPeriod(period: HalfDayPeriod?): String? = period?.name

    @TypeConverter
    fun toHalfDayPeriod(period: String?): HalfDayPeriod? = period?.let { HalfDayPeriod.valueOf(it) }

    @TypeConverter
    fun fromLeaveStatus(status: LeaveStatus): String = status.name

    @TypeConverter
    fun toLeaveStatus(status: String): LeaveStatus = LeaveStatus.valueOf(status)

    @TypeConverter
    fun fromDayOfWeekList(days: List<DayOfWeek>): String {
        return days.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toDayOfWeekList(data: String): List<DayOfWeek> {
        if (data.isBlank()) return emptyList()
        return data.split(",").map { DayOfWeek.valueOf(it) }
    }
}
