package com.company.attendanceapp.presentation.screens.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.company.attendanceapp.presentation.theme.Secondary
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AdminLiveMapScreen() {
    val bangalore = LatLng(12.9716, 77.5946)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(bangalore, 12f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // Mock Markers
            Marker(
                state = MarkerState(position = LatLng(12.9716, 77.5946)),
                title = "Riya Sharma",
                snippet = "Checked In: 09:02 AM"
            )
            Marker(
                state = MarkerState(position = LatLng(12.9352, 77.6245)), // Koramangala
                title = "Arjun Mehta",
                snippet = "Late: 09:45 AM"
            )

            // Geofence Visual
            Circle(
                center = bangalore,
                radius = 1000.0,
                strokeColor = Secondary,
                fillColor = Secondary.copy(alpha = 0.2f)
            )
        }
    }
}
