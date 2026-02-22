package com.company.attendanceapp.data.remote.sheets

import com.company.attendanceapp.data.remote.sheets.dto.AppendValuesRequest
import com.company.attendanceapp.data.remote.sheets.dto.AppendValuesResponse
import com.company.attendanceapp.data.remote.sheets.dto.BatchUpdateRequest
import com.company.attendanceapp.data.remote.sheets.dto.BatchUpdateResponse
import com.company.attendanceapp.data.remote.sheets.dto.SheetValuesResponse
import com.company.attendanceapp.data.remote.sheets.dto.UpdateValuesRequest
import com.company.attendanceapp.data.remote.sheets.dto.UpdateValuesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleSheetsApi {

    @GET("v4/spreadsheets/{spreadsheetId}/values/{range}")
    suspend fun getValues(
        @Path("spreadsheetId") spreadsheetId: String,
        @Path("range") range: String,
        @Header("Authorization") token: String
    ): SheetValuesResponse

    @PUT("v4/spreadsheets/{spreadsheetId}/values/{range}")
    suspend fun updateValues(
        @Path("spreadsheetId") spreadsheetId: String,
        @Path("range") range: String,
        @Query("valueInputOption") valueInputOption: String = "USER_ENTERED",
        @Header("Authorization") token: String,
        @Body body: UpdateValuesRequest
    ): UpdateValuesResponse

    @POST("v4/spreadsheets/{spreadsheetId}/values/{range}:append")
    suspend fun appendValues(
        @Path("spreadsheetId") spreadsheetId: String,
        @Path("range") range: String,
        @Query("valueInputOption") valueInputOption: String = "USER_ENTERED",
        @Query("insertDataOption") insertDataOption: String = "INSERT_ROWS",
        @Header("Authorization") token: String,
        @Body body: AppendValuesRequest
    ): AppendValuesResponse

    @POST("v4/spreadsheets/{spreadsheetId}/values:batchUpdate")
    suspend fun batchUpdate(
        @Path("spreadsheetId") spreadsheetId: String,
        @Header("Authorization") token: String,
        @Body body: BatchUpdateRequest
    ): BatchUpdateResponse
}
