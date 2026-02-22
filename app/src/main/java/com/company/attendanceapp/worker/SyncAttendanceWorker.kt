package com.company.attendanceapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result
import androidx.work.WorkerParameters
import com.company.attendanceapp.core.common.Constants
import com.company.attendanceapp.core.common.Resource
import com.company.attendanceapp.data.local.database.dao.EmployeeDao
import com.company.attendanceapp.data.local.database.entities.EmployeeEntity
import com.company.attendanceapp.data.remote.sheets.GoogleSheetsDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SyncAttendanceWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val sheetsDataSource: GoogleSheetsDataSource,
    private val employeeDao: EmployeeDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Starting sync...")
            // 1. Fetch Employees from Google Sheets
            val token = "YOUR_OAUTH_TOKEN" // In production, this comes from TokenManager

            when (val result = sheetsDataSource.getValues(Constants.SHEET_RANGE_EMPLOYEES, token)) {
                is Resource.Success -> {
                    val rows = result.data?.values ?: emptyList()
                    val entities = rows.mapNotNull { row ->
                        parseEmployeeRow(row)
                    }
                    if (entities.isNotEmpty()) {
                        employeeDao.insertEmployees(entities)
                        Log.d(TAG, "Synced ${entities.size} employees")
                    }
                    Result.success()
                }
                is Resource.Error -> {
                    Log.e(TAG, "Sync failed: ${result.message}")
                    Result.retry()
                }
                is Resource.Loading -> Result.retry()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Sync exception", e)
            Result.failure()
        }
    }

    private fun parseEmployeeRow(row: List<String>): EmployeeEntity? {
        // A: employee_id | B: name | C: email | ...
        if (row.isEmpty()) return null
        return try {
            EmployeeEntity(
                employeeId = row.getOrElse(0) { "" },
                name = row.getOrElse(1) { "" },
                email = row.getOrElse(2) { "" },
                phone = row.getOrElse(3) { "" },
                department = row.getOrElse(4) { "" },
                role = row.getOrElse(5) { "EMPLOYEE" },
                managerId = row.getOrNull(6)?.takeIf { it.isNotBlank() },
                shiftId = row.getOrNull(7)?.takeIf { it.isNotBlank() },
                locationId = row.getOrNull(8)?.takeIf { it.isNotBlank() },
                remoteAllowed = row.getOrElse(9) { "FALSE" }.toBoolean(),
                probationFlag = row.getOrElse(10) { "FALSE" }.toBoolean(),
                status = row.getOrElse(11) { "ACTIVE" },
                profilePhotoUrl = row.getOrNull(12)?.takeIf { it.isNotBlank() },
                createdAt = System.currentTimeMillis() // Simplified
            )
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val TAG = "SyncAttendanceWorker"
    }
}
