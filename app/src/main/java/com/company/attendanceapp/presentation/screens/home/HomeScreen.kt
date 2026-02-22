package com.company.attendanceapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.presentation.navigation.Screen
import com.company.attendanceapp.presentation.theme.Error
import com.company.attendanceapp.presentation.theme.ErrorContainer
import com.company.attendanceapp.presentation.theme.Primary
import com.company.attendanceapp.presentation.theme.Secondary
import com.company.attendanceapp.presentation.theme.StatusAbsent
import com.company.attendanceapp.presentation.theme.StatusLate
import com.company.attendanceapp.presentation.theme.StatusPresent
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Avatar Placeholder
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.userName.take(1),
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Good Morning, ${state.userName} \uD83D\uDC4B",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Text(
                                text = state.currentDate,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Notifications.route) }) {
                        BadgedBox(
                            badge = {
                                if (state.unreadNotifications > 0) {
                                    Badge { Text(state.unreadNotifications.toString()) }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White
                            )
                        }
                    }
                    IconButton(onClick = { /* More options */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // 1. Status Hero Card
            item {
                StatusHeroCard(
                    status = state.todayAttendance?.status ?: AttendanceStatus.ABSENT, // Default to absent if no data yet
                    checkInTime = state.todayAttendance?.checkInTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "--:--",
                    checkOutTime = state.todayAttendance?.checkOutTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "--:--",
                    duration = "2h 34m", // TODO: Calculate dynamically
                    isCheckedIn = state.isCheckedIn,
                    onCheckAction = {
                        if (state.isCheckedIn) {
                            // Navigate to checkout logic (or same screen with diff state)
                            navController.navigate(Screen.CheckIn.route)
                        } else {
                            navController.navigate(Screen.CheckIn.route)
                        }
                    }
                )
            }

            // 2. Shift Info Card
            item {
                ShiftInfoCard()
            }

            // 3. Weekly Overview (Stub)
            item {
                WeeklyOverviewCard()
            }

            // 4. Quick Actions
            item {
                QuickActionsGrid(navController)
            }

            // 5. Recent Activity
            item {
                RecentActivitySection()
            }

            item { Spacer(modifier = Modifier.height(80.dp)) } // Bottom padding for nav bar
        }
    }
}

@Composable
fun StatusHeroCard(
    status: AttendanceStatus,
    checkInTime: String,
    checkOutTime: String,
    duration: String,
    isCheckedIn: Boolean,
    onCheckAction: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Primary, Secondary)
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                // Top Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today's Status",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Surface(
                        color = when(status) {
                            AttendanceStatus.PRESENT -> StatusPresent
                            AttendanceStatus.LATE -> StatusLate
                            else -> Color.White.copy(alpha = 0.2f)
                        },
                        shape = CircleShape
                    ) {
                        Text(
                            text = status.name,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Clock Display (Mock)
                Text(
                    text = "09:41:32 AM", // TODO: Live clock
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Login, contentDescription = null, tint = Color.White.copy(0.8f))
                        Text("Check In", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(0.6f))
                        Text(checkInTime, style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Divider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp)
                            .background(Color.White.copy(0.3f))
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Logout, contentDescription = null, tint = Color.White.copy(0.8f))
                        Text("Check Out", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(0.6f))
                        Text(checkOutTime, style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    if (isCheckedIn) {
                        Divider(
                            modifier = Modifier
                                .height(40.dp)
                                .width(1.dp)
                                .background(Color.White.copy(0.3f))
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Timer, contentDescription = null, tint = Color.White.copy(0.8f))
                            Text("Duration", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(0.6f))
                            Text(duration, style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action Button
                Button(
                    onClick = onCheckAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCheckedIn) ErrorContainer else Color.White,
                        contentColor = if (isCheckedIn) Error else Primary
                    )
                ) {
                    Text(
                        text = if (isCheckedIn) "CHECK OUT" else "CHECK IN",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ShiftInfoCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Your Shift Today", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
                    Text("Morning Shift", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("09:00 AM — 06:00 PM", style = MaterialTheme.typography.bodyMedium, color = Primary)
                }
                // Mock Progress
                 CircularProgressIndicator(
                     progress = 0.67f,
                     modifier = Modifier.size(56.dp),
                     color = Primary,
                     trackColor = MaterialTheme.colorScheme.surfaceVariant
                 )
            }
        }
    }
}

@Composable
fun WeeklyOverviewCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("This Week", style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = { /*TODO*/ }) {
                    Text("View Details →")
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
            Text("Chart Placeholder (Vico)", modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun QuickActionsGrid(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuickActionItem("Schedule", Icons.Default.CalendarMonth, Modifier.weight(1f)) {
            navController.navigate(Screen.Schedule.route)
        }
        QuickActionItem("Apply Leave", Icons.Default.Login, Modifier.weight(1f)) { // TODO Icon
            navController.navigate(Screen.Leave.route)
        }
    }
}

@Composable
fun QuickActionItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(88.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = Primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun RecentActivitySection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Recent Activity", style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = { /*TODO*/ }) {
                Text("See All")
            }
        }
        // Mock Item
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(0.5.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Feb", style = MaterialTheme.typography.labelSmall, color = Primary)
                    Text("21", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("09:02 AM → 06:15 PM", style = MaterialTheme.typography.bodyMedium)
                    Text("9h 13m worked", style = MaterialTheme.typography.labelMedium, color = Secondary)
                }
            }
        }
    }
}
