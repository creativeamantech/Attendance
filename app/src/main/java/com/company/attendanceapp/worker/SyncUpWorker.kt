package com.company.attendanceapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.company.attendanceapp.core.common.Resource
import com.company.attendanceapp.data.local.database.dao.SyncQueueDao
import com.company.attendanceapp.data.remote.sheets.GoogleSheetsDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SyncUpWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val sheetsDataSource: GoogleSheetsDataSource,
    private val syncQueueDao: SyncQueueDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Starting sync up...")
            val pendingItems = syncQueueDao.getPendingItems()
            if (pendingItems.isEmpty()) {
                return@withContext Result.success()
            }

            val token = "YOUR_OAUTH_TOKEN" // Should be fetched from TokenManager

            for (item in pendingItems) {
                // Parse payload and push to sheets
                // For now, assuming payload is just values list for a row
                // In production, this would be more complex JSON parsing

                // Example: Mock logic
                val range = "${item.tableName}!A:Z"
                val values = listOf("Mock", "Data", "From", "Queue") // parse item.dataPayload

                val result = sheetsDataSource.appendRow(range, values, token)

                if (result is Resource.Success) {
                    syncQueueDao.deleteItem(item)
                } else {
                    // Handle failure (increment retry or abort)
                    Log.e(TAG, "Failed to sync item ${item.id}")
                }
            }

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Sync up exception", e)
            Result.retry()
        }
    }

    companion object {
        private const val TAG = "SyncUpWorker"
    }
}
