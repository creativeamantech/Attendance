package com.company.attendanceapp.data.repository

import com.company.attendanceapp.data.local.database.dao.ShiftDao
import com.company.attendanceapp.data.local.mapper.toDomain
import com.company.attendanceapp.data.local.mapper.toEntity
import com.company.attendanceapp.domain.model.Resource
import com.company.attendanceapp.domain.model.Shift
import com.company.attendanceapp.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShiftRepositoryImpl @Inject constructor(
    private val shiftDao: ShiftDao
) : ShiftRepository {

    override fun getShiftById(shiftId: String): Flow<Resource<Shift>> {
        return shiftDao.getShiftById(shiftId).map { entity ->
            if (entity != null) {
                Resource.Success(entity.toDomain())
            } else {
                Resource.Error("Shift not found")
            }
        }
    }

    override fun getAllShifts(): Flow<Resource<List<Shift>>> {
        return shiftDao.getAllShifts().map { entities ->
            Resource.Success(entities.map { it.toDomain() })
        }
    }

    override fun addShift(shift: Shift): Flow<Resource<Unit>> = kotlinx.coroutines.flow.flow {
        emit(Resource.Loading())
        try {
            shiftDao.insertShift(shift.toEntity())
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override fun updateShift(shift: Shift): Flow<Resource<Unit>> = kotlinx.coroutines.flow.flow {
        emit(Resource.Loading())
        try {
            shiftDao.updateShift(shift.toEntity())
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}
