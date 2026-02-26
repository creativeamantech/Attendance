package com.company.attendanceapp.domain.model

data class GeofenceResult(
    val isInside: Boolean,
    val distanceFromCenter: Double,
    val distanceFromBoundary: Double,
    val locationStatus: LocationStatus
)
