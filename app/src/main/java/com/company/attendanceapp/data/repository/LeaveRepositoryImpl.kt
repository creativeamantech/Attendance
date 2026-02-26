package com.company.attendanceapp.data.repository

import com.company.attendanceapp.data.local.database.dao.LeaveRequestDao
import com.company.attendanceapp.data.local.mapper.toDomain
import com.company.attendanceapp.data.local.mapper.toEntity
import com.company.attendanceapp.domain.model.LeaveRequest
import com.company.attendanceapp.domain.model.LeaveStatus
import com.company.attendanceapp.domain.model.LeaveType
import com.company.attendanceapp.domain.model.Resource
import com.company.attendanceapp.domain.repository.LeaveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeaveRepositoryImpl @Inject constructor(
    private val leaveRequestDao: LeaveRequestDao
) : LeaveRepository {

    override fun applyLeave(request: LeaveRequest): Flow<Resource<Unit>> = kotlinx.coroutines.flow.flow {
        emit(Resource.Loading())
        try {
            leaveRequestDao.insertLeave(request.toEntity())
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override fun getLeavesByEmployee(employeeId: String): Flow<Resource<List<LeaveRequest>>> {
        return leaveRequestDao.getLeavesByEmployee(employeeId).map { entities ->
            Resource.Success(entities.map { it.toDomain() })
        }
    }

    override fun updateLeaveStatus(
        leaveId: String,
        status: LeaveStatus,
        approvedBy: String?
    ): Flow<Resource<Unit>> {
        // Implement status update logic
        return kotlinx.coroutines.flow.flow {
            emit(Resource.Loading())
        }
    }

    override fun getLeaveBalance(employeeId: String): Flow<Resource<Map<LeaveType, Int>>> {
        // Implement leave balance calculation
        return kotlinx.coroutines.flow.flow {
            emit(Resource.Loading())
        }
    }

    override fun getPendingLeaves(): Flow<Resource<List<LeaveRequest>>> {
        return leaveRequestDao.getPendingLeaves().map { entities ->
            Resource.Success(entities.map { it.toDomain() })
        }
    }
}
