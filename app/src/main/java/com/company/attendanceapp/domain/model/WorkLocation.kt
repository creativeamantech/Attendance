package com.company.attendanceapp.domain.model

enum class LocationType {
    OFFICE,
    HOME,
    CLIENT,
    FIELD
}

data class WorkLocation(
    val locationId: String,
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
    val radiusMeters: Double,
    val address: String,
    val city: String,
    val timezone: String,
    val locationType: LocationType,
    val isActive: Boolean
)
