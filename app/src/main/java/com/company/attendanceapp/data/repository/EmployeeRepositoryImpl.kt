package com.company.attendanceapp.data.repository

import com.company.attendanceapp.data.local.database.dao.EmployeeDao
import com.company.attendanceapp.data.local.mapper.toDomain
import com.company.attendanceapp.data.local.mapper.toEntity
import com.company.attendanceapp.domain.model.Employee
import com.company.attendanceapp.domain.model.Resource
import com.company.attendanceapp.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeRepositoryImpl @Inject constructor(
    private val employeeDao: EmployeeDao
) : EmployeeRepository {

    override fun getEmployeeById(id: String): Flow<Resource<Employee>> {
        return employeeDao.getEmployeeById(id).map { entity ->
            if (entity != null) {
                Resource.Success(entity.toDomain())
            } else {
                Resource.Error("Employee not found")
            }
        }
    }

    override fun getAllEmployees(): Flow<Resource<List<Employee>>> {
        return employeeDao.getAllEmployees().map { entities ->
            Resource.Success(entities.map { it.toDomain() })
        }
    }

    override fun addEmployee(employee: Employee): Flow<Resource<Unit>> {
        // Implement logic to add employee to DB and sync queue
        return kotlinx.coroutines.flow.flow {
            emit(Resource.Loading())
            try {
                employeeDao.insertEmployee(employee.toEntity())
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override fun updateEmployee(employee: Employee): Flow<Resource<Unit>> {
        // Implement logic to update employee in DB and sync queue
        return kotlinx.coroutines.flow.flow {
            emit(Resource.Loading())
            try {
                employeeDao.updateEmployee(employee.toEntity())
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override fun deleteEmployee(id: String): Flow<Resource<Unit>> {
        // Implement logic to delete employee from DB and sync queue
        return kotlinx.coroutines.flow.flow {
            emit(Resource.Loading())
            try {
                employeeDao.deleteEmployee(id)
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override fun getEmployeeByEmail(email: String): Flow<Resource<Employee>> {
        return employeeDao.getEmployeeByEmail(email).map { entity ->
            if (entity != null) {
                Resource.Success(entity.toDomain())
            } else {
                Resource.Error("Employee not found")
            }
        }
    }
}
