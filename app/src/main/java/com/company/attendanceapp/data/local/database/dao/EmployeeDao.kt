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
    fun getEmployeeById(id: String): Flow<EmployeeEntity?>

    @Query("SELECT * FROM employees")
    fun getAllEmployees(): Flow<List<EmployeeEntity>>

    @Query("SELECT * FROM employees WHERE email = :email")
    fun getEmployeeByEmail(email: String): Flow<EmployeeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(entity: EmployeeEntity)

    @Update
    suspend fun updateEmployee(entity: EmployeeEntity)

    @Query("DELETE FROM employees WHERE employeeId = :id")
    suspend fun deleteEmployee(id: String)
}
