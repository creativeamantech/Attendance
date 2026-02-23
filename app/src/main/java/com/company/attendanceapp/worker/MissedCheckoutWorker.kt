package com.company.attendanceapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class MissedCheckoutWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Checking for missed checkouts...")
            val employeeId = inputData.getString("employee_id")

            // TODO: Check if user is still checked in and shift + 2 hours has passed
            // If so, auto checkout or send notification

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Missed checkout check failed", e)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "MissedCheckoutWorker"
    }
}
