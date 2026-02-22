package com.company.attendanceapp.data.remote.sheets.dto

data class SheetValuesResponse(
    val range: String,
    val majorDimension: String,
    val values: List<List<String>>?
)

data class UpdateValuesRequest(
    val values: List<List<String>>
)

data class UpdateValuesResponse(
    val spreadsheetId: String,
    val updatedRange: String,
    val updatedRows: Int,
    val updatedColumns: Int,
    val updatedCells: Int
)

data class AppendValuesRequest(
    val values: List<List<String>>
)

data class AppendValuesResponse(
    val spreadsheetId: String,
    val tableRange: String,
    val updates: UpdateValuesResponse?
)

data class BatchUpdateRequest(
    val requests: List<Any> // Simplified for now
)

data class BatchUpdateResponse(
    val spreadsheetId: String
)
