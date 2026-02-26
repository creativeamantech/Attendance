package com.company.attendanceapp.core.location

import com.company.attendanceapp.domain.model.GeofenceResult
import com.company.attendanceapp.domain.model.LocationStatus
import kotlin.math.*

object HaversineCalculator {
    private const val EARTH_RADIUS_METERS = 6_371_000.0

    fun distanceBetween(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLng / 2) * sin(dLng / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return EARTH_RADIUS_METERS * c
    }

    fun isInsideGeofence(
        userLat: Double,
        userLng: Double,
        zoneLat: Double,
        zoneLng: Double,
        radiusMeters: Double,
        gpsAccuracy: Float
    ): GeofenceResult {
        val distance = distanceBetween(userLat, userLng, zoneLat, zoneLng)
        // Allow user to be within radius + accuracy
        val effectiveRadius = radiusMeters + gpsAccuracy
        val isInside = distance <= effectiveRadius

        val status = if (isInside) LocationStatus.VALID else LocationStatus.OUT_OF_ZONE

        return GeofenceResult(
            isInside = isInside,
            distanceFromCenter = distance,
            distanceFromBoundary = max(0.0, distance - radiusMeters),
            locationStatus = status
        )
    }
}
