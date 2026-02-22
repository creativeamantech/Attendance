package com.company.attendanceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.company.attendanceapp.presentation.screens.splash.SplashScreen
import com.company.attendanceapp.presentation.theme.AttendanceAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttendanceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AttendanceApp()
                }
            }
        }
    }
}

@Composable
fun AttendanceApp() {
    // This will be replaced by the Navigation Graph
    // For now, just a placeholder or the Splash Screen
    SplashScreen(
        onNavigateToLogin = { /* Navigate to Login */ },
        onNavigateToOnboarding = { /* Navigate to Onboarding */ },
        onNavigateToHome = { /* Navigate to Home */ }
    )
}
