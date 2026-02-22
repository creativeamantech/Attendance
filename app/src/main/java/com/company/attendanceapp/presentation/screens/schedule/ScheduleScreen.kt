package com.company.attendanceapp.presentation.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.attendanceapp.presentation.theme.Error
import com.company.attendanceapp.presentation.theme.Primary
import com.company.attendanceapp.presentation.theme.PrimaryContainer
import com.company.attendanceapp.presentation.theme.Secondary
import com.company.attendanceapp.presentation.theme.TertiaryContainer
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    navController: NavController,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    if (state.selectedShift != null) {
        ModalBottomSheet(onDismissRequest = { viewModel.onEvent(ScheduleEvent.ClearSelection) }) {
            ShiftDetailContent(shift = state.selectedShift!!)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Schedule") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Week Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.onEvent(ScheduleEvent.PreviousWeek) }) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Week")
                }

                val formatter = DateTimeFormatter.ofPattern("MMM d")
                Text(
                    text = "${state.weekStartDate.format(formatter)} - ${state.weekEndDate.format(formatter)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = { viewModel.onEvent(ScheduleEvent.NextWeek) }) {
                    Icon(Icons.Default.ChevronRight, contentDescription = "Next Week")
                }
            }

            // Daily Cards
            LazyColumn(
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.shifts) { shift ->
                    ShiftCard(
                        shift = shift,
                        onClick = { viewModel.onEvent(ScheduleEvent.SelectShift(shift)) }
                    )
                }
            }
        }
    }
}

@Composable
fun ShiftCard(shift: ShiftItem, onClick: () -> Unit) {
    val isToday = shift.date == java.time.LocalDate.now()

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isToday) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = shift.date.format(DateTimeFormatter.ofPattern("EEE, d MMM")),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    if (isToday) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = PrimaryContainer,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "TODAY",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Primary
                            )
                        }
                    }
                }

                if (shift.isWorkingDay) {
                    Surface(
                        color = if (shift.status == "COMPLETED") Secondary.copy(alpha = 0.1f) else Color.Gray.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(50),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (shift.status == "COMPLETED") Secondary else Color.Gray)
                    ) {
                        Text(
                            text = shift.status,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (shift.status == "COMPLETED") Secondary else Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (shift.isWorkingDay) {
                // Timeline
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Primary.copy(alpha = 0.2f))
                ) {
                    if (shift.status == "COMPLETED") {
                        Box(
                            modifier = Modifier
                                .fillMaxSize() // Simplified: fill if completed
                                .background(Primary)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Start", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Text(shift.startTime ?: "--", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Text("End", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Text(shift.endTime ?: "--", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Text("Break", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Text("${shift.breakMinutes}m", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                // Weekend / Holiday
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TertiaryContainer.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Weekend, contentDescription = null, tint = Color.Gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = shift.shiftName, // "Weekend Off"
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShiftDetailContent(shift: ShiftItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .padding(bottom = 32.dp)
    ) {
        Text(
            text = shift.shiftName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            color = PrimaryContainer,
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = "Fixed Shift",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelMedium,
                color = Primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                DetailRow("Start Time", shift.startTime ?: "--")
                DetailRow("End Time", shift.endTime ?: "--")
                DetailRow("Break Duration", "${shift.breakMinutes} minutes")
            }
            Column(modifier = Modifier.weight(1f)) {
                DetailRow("Grace Period", "10 minutes")
                DetailRow("Min Hours", "8 hours")
                DetailRow("Overtime After", "9 hours")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        DetailRow("Working Days", "Mon Tue Wed Thu Fri")
        DetailRow("Location(s)", "Tech Park HQ, Client Site B")

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Request Shift Change", color = Primary)
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}
