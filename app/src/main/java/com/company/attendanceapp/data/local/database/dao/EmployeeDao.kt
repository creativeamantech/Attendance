package com.company.attendanceapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.company.attendanceapp.data.local.database.entities.EmployeeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees WHERE employeeId = :id")
    suspend fun getEmployeeById(id: String): EmployeeEntity?

    @Query("SELECT * FROM employees WHERE email = :email")
    suspend fun getEmployeeByEmail(email: String): EmployeeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: EmployeeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployees(employees: List<EmployeeEntity>)

    @Update
    suspend fun updateEmployee(employee: EmployeeEntity)

    @Query("SELECT * FROM employees")
    fun getAllEmployees(): Flow<List<EmployeeEntity>>

    @Query("SELECT * FROM employees WHERE status = 'ACTIVE'")
    fun getActiveEmployees(): Flow<List<EmployeeEntity>>
}
