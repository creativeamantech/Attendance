package com.company.attendanceapp.presentation.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.attendanceapp.presentation.screens.history.StatusChip
import com.company.attendanceapp.presentation.theme.Error
import com.company.attendanceapp.presentation.theme.Primary
import com.company.attendanceapp.presentation.theme.Secondary
import com.company.attendanceapp.presentation.theme.StatusLate
import com.company.attendanceapp.presentation.theme.Tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    navController: NavController,
    viewModel: AdminDashboardViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Dashboard", "Live Map", "Employees")

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text("Admin Panel") })
                ScrollableTabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> DashboardContent(state)
                1 -> AdminLiveMapScreen() // Implemented in separate file
                2 -> Text("Employee List Placeholder") // Placeholder
            }
        }
    }
}

@Composable
fun DashboardContent(state: AdminDashboardUiState) {
    LazyColumn(
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Stats Grid
        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(340.dp) // Approximate height for 4 rows
            ) {
                item { StatCard("Total Employees", state.stats.totalEmployees.toString(), Primary) }
                item { StatCard("Present Today", state.stats.presentToday.toString(), Secondary) }
                item { StatCard("Late Arrivals", state.stats.lateArrivals.toString(), StatusLate) }
                item { StatCard("Out of Zone", state.stats.outOfZone.toString(), Error) }
                item { StatCard("Absent", state.stats.absent.toString(), Error) }
                item { StatCard("On Leave", state.stats.onLeave.toString(), Tertiary) }
            }
        }

        // Alert Panel
        if (state.stats.outOfZone > 0 || state.stats.lateArrivals > 0) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Tertiary.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Tertiary)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Tertiary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("${state.stats.outOfZone + state.stats.lateArrivals} employees flagged today", fontWeight = FontWeight.Bold)
                            Text("View details", style = MaterialTheme.typography.bodySmall, color = Primary)
                        }
                    }
                }
            }
        }

        // Live Feed
        item { Text("Live Attendance Feed", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }

        items(state.liveFeed) { item ->
            LiveFeedItem(item)
        }
    }
}

@Composable
fun StatCard(label: String, value: String, color: Color) {
    Card(
        modifier = Modifier.height(80.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = color)
            Text(label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@Composable
fun LiveFeedItem(item: LiveAttendanceItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(item.name.take(1), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold)
                Text(item.location, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                StatusChip(item.status)
                Text(item.time, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
