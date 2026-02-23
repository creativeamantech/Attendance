package com.company.attendanceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.company.attendanceapp.core.security.TokenManager
import com.company.attendanceapp.presentation.navigation.AppNavGraph
import com.company.attendanceapp.presentation.theme.AttendanceAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttendanceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userRole by tokenManager.userRole.collectAsState(initial = null)
                    AttendanceApp(userRole = userRole)
                }
            }
        }
    }
}

@Composable
fun AttendanceApp(userRole: String?) {
    val navController = rememberNavController()
    AppNavGraph(navController = navController, userRole = userRole)
}
