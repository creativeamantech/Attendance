package com.company.attendanceapp.domain.repository

import com.company.attendanceapp.domain.model.LeaveRequest
import com.company.attendanceapp.domain.model.LeaveStatus
import com.company.attendanceapp.domain.model.LeaveType
import com.company.attendanceapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface LeaveRepository {
    fun applyLeave(request: LeaveRequest): Flow<Resource<Unit>>
    fun getLeavesByEmployee(employeeId: String): Flow<Resource<List<LeaveRequest>>>
    fun updateLeaveStatus(leaveId: String, status: LeaveStatus, approvedBy: String?): Flow<Resource<Unit>>
    fun getLeaveBalance(employeeId: String): Flow<Resource<Map<LeaveType, Int>>>
    fun getPendingLeaves(): Flow<Resource<List<LeaveRequest>>>
}
