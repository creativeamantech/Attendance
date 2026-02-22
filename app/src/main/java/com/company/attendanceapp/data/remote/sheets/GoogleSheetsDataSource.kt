package com.company.attendanceapp.data.remote.sheets

import com.company.attendanceapp.core.common.Resource
import com.company.attendanceapp.data.remote.sheets.dto.AppendValuesRequest
import com.company.attendanceapp.data.remote.sheets.dto.AppendValuesResponse
import com.company.attendanceapp.data.remote.sheets.dto.SheetValuesResponse
import com.company.attendanceapp.data.remote.sheets.dto.UpdateValuesRequest
import com.company.attendanceapp.data.remote.sheets.dto.UpdateValuesResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleSheetsDataSource @Inject constructor(
    private val sheetsApi: GoogleSheetsApi
) {

    private val spreadsheetId = "YOUR_SPREADSHEET_ID" // Should be in local.properties or fetched dynamically

    suspend fun getValues(range: String, token: String): Resource<SheetValuesResponse> {
        return try {
            val response = sheetsApi.getValues(spreadsheetId, range, "Bearer $token")
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to fetch sheets data")
        }
    }

    suspend fun appendRow(range: String, values: List<String>, token: String): Resource<AppendValuesResponse> {
        return try {
            val request = AppendValuesRequest(listOf(values))
            val response = sheetsApi.appendValues(spreadsheetId, range, token = "Bearer $token", body = request)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to append row")
        }
    }

    suspend fun updateRow(range: String, values: List<String>, token: String): Resource<UpdateValuesResponse> {
        return try {
            val request = UpdateValuesRequest(listOf(values))
            val response = sheetsApi.updateValues(spreadsheetId, range, token = "Bearer $token", body = request)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update row")
        }
    }
}
