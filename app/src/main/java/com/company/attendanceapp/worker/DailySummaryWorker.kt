package com.company.attendanceapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.company.attendanceapp.data.local.database.dao.EmployeeDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class DailySummaryWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val employeeDao: EmployeeDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Running daily summary...")
            // TODO: Calculate daily stats and maybe push notification to admin

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Daily summary failed", e)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "DailySummaryWorker"
    }
}
