package com.company.attendanceapp.domain.repository

import com.company.attendanceapp.domain.model.Employee
import com.company.attendanceapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    fun getEmployeeById(id: String): Flow<Resource<Employee>>
    fun getAllEmployees(): Flow<Resource<List<Employee>>>
    fun addEmployee(employee: Employee): Flow<Resource<Unit>>
    fun updateEmployee(employee: Employee): Flow<Resource<Unit>>
    fun deleteEmployee(id: String): Flow<Resource<Unit>>
    fun getEmployeeByEmail(email: String): Flow<Resource<Employee>>
}
