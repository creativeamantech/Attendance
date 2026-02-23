package com.company.attendanceapp.presentation.screens.checkin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.attendanceapp.R
import com.company.attendanceapp.presentation.components.common.AppLottieAnimation
import com.company.attendanceapp.presentation.theme.Error
import com.company.attendanceapp.presentation.theme.ErrorContainer
import com.company.attendanceapp.presentation.theme.Primary
import com.company.attendanceapp.presentation.theme.Secondary
import com.company.attendanceapp.presentation.theme.SecondaryContainer
import com.company.attendanceapp.presentation.theme.StatusPresent
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CheckInScreen(
    navController: NavController,
    viewModel: CheckInViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    // Bangalore Tech Park Coordinates (Mock)
    val officeLocation = LatLng(12.9716, 77.5946)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(officeLocation, 15f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Map Background
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = officeLocation),
                title = "Office Location",
                snippet = "Tech Park"
            )
            Circle(
                center = officeLocation,
                radius = 200.0, // 200m geofence
                strokeColor = if (state.isInsideZone) Secondary else Error,
                fillColor = (if (state.isInsideZone) Secondary else Error).copy(alpha = 0.2f)
            )
        }

        // Sliding Panel (Bottom Sheet style)
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                // Handle Bar
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(40.dp)
                        .height(4.dp)
                        .background(Color.Gray.copy(alpha = 0.3f), CircleShape)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Location Status Banner
                if (state.isInsideZone) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SecondaryContainer, RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Secondary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "You are inside the office zone",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ErrorContainer, RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = Error)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "You are outside the permitted area",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Error
                            )
                            Text(
                                text = "Distance: ${state.distanceFromZone.toInt()}m from office",
                                style = MaterialTheme.typography.labelSmall,
                                color = Error
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Current Location Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Detected Location", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                        Text(state.currentLocationName, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                        Text("Accuracy: ±12m", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                    IconButton(onClick = { viewModel.onEvent(CheckInEvent.RefreshLocation) }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh GPS")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action Button
                if (state.isCheckedIn) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { /* Break */ },
                            modifier = Modifier.weight(1f).height(56.dp)
                        ) {
                            Text("BREAK")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { viewModel.onEvent(CheckInEvent.CheckOutClicked) },
                            modifier = Modifier.weight(1f).height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Error)
                        ) {
                            Text("CHECK OUT")
                        }
                    }
                } else {
                    Button(
                        onClick = { viewModel.onEvent(CheckInEvent.CheckInClicked) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        enabled = state.isInsideZone && !state.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = Secondary)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("MARK CHECK-IN")
                        }
                    }

                    if (!state.isInsideZone) {
                        TextButton(
                            onClick = { /* Override */ },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Request Override", color = Error)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(80.dp)) // Bottom padding for nav bar
            }
        }

        // Success Overlay
        AnimatedVisibility(
            visible = state.checkInSuccess,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Secondary.copy(alpha = 0.95f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AppLottieAnimation(
                        resId = R.raw.anim_success,
                        modifier = Modifier.size(200.dp),
                        shouldAnimate = true
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Checked In Successfully!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = state.checkInTime ?: "--:--", // TODO: Format time
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}
