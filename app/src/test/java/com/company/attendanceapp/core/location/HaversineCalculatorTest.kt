package com.company.attendanceapp.core.location

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HaversineCalculatorTest {

    @Test
    fun `distanceBetween should return correct distance`() {
        val lat1 = 12.9716 // Bangalore
        val lng1 = 77.5946
        val lat2 = 13.0827 // Chennai
        val lng2 = 80.2707

        // Distance approx 290 km
        val distance = HaversineCalculator.distanceBetween(lat1, lng1, lat2, lng2)

        // Allow some margin for floating point
        assertEquals(290000.0, distance, 5000.0) // Within 5km margin
    }

    @Test
    fun `isInsideGeofence should return true when within radius`() {
        val centerLat = 12.9716
        val centerLng = 77.5946
        val userLat = 12.9717 // Very close
        val userLng = 77.5947
        val radius = 100.0
        val accuracy = 10f

        val result = HaversineCalculator.isInsideGeofence(userLat, userLng, centerLat, centerLng, radius, accuracy)

        assertTrue(result.isInside)
    }

    @Test
    fun `isInsideGeofence should return false when outside radius`() {
        val centerLat = 12.9716
        val centerLng = 77.5946
        val userLat = 13.9716 // Far away
        val userLng = 77.5946
        val radius = 100.0
        val accuracy = 10f

        val result = HaversineCalculator.isInsideGeofence(userLat, userLng, centerLat, centerLng, radius, accuracy)

        assertFalse(result.isInside)
    }
}
