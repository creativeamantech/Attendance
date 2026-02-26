package com.company.attendanceapp.domain.repository

import com.company.attendanceapp.domain.model.Resource
import com.company.attendanceapp.domain.model.Shift
import kotlinx.coroutines.flow.Flow

interface ShiftRepository {
    fun getShiftById(shiftId: String): Flow<Resource<Shift>>
    fun getAllShifts(): Flow<Resource<List<Shift>>>
    fun addShift(shift: Shift): Flow<Resource<Unit>>
    fun updateShift(shift: Shift): Flow<Resource<Unit>>
}
