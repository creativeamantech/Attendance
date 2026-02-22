package com.company.attendanceapp.core.location

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.pow

object HaversineCalculator {
    private const val EARTH_RADIUS_METERS = 6_371_000.0

    fun distanceBetween(
        lat1: Double, lng1: Double,
        lat2: Double, lng2: Double
    ): Double {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) *
                cos(Math.toRadians(lat2)) *
                sin(dLng / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return EARTH_RADIUS_METERS * c
    }

    fun isInsideGeofence(
        userLat: Double, userLng: Double,
        zoneLat: Double, zoneLng: Double,
        radiusMeters: Double,
        gpsAccuracy: Float
    ): GeofenceResult {
        val distance = distanceBetween(userLat, userLng, zoneLat, zoneLng)
        val effectiveRadius = radiusMeters + gpsAccuracy // account for GPS error
        return GeofenceResult(
            isInside = distance <= effectiveRadius,
            distanceFromCenter = distance,
            distanceFromBoundary = distance - radiusMeters
        )
    }
}

data class GeofenceResult(
    val isInside: Boolean,
    val distanceFromCenter: Double,
    val distanceFromBoundary: Double
)
