package com.company.attendanceapp.presentation.screens.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.attendanceapp.domain.model.Attendance
import com.company.attendanceapp.domain.model.AttendanceStatus
import com.company.attendanceapp.presentation.theme.Error
import com.company.attendanceapp.presentation.theme.Primary
import com.company.attendanceapp.presentation.theme.PrimaryContainer
import com.company.attendanceapp.presentation.theme.Secondary
import com.company.attendanceapp.presentation.theme.SecondaryContainer
import com.company.attendanceapp.presentation.theme.StatusAbsent
import com.company.attendanceapp.presentation.theme.StatusHoliday
import com.company.attendanceapp.presentation.theme.StatusLate
import com.company.attendanceapp.presentation.theme.StatusOnLeave
import com.company.attendanceapp.presentation.theme.StatusPresent
import com.company.attendanceapp.presentation.theme.Tertiary
import com.company.attendanceapp.presentation.theme.TertiaryContainer
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet && state.selectedDate != null) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            DayDetailContent(
                date = state.selectedDate!!,
                attendance = state.selectedDateAttendance
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance History") },
                actions = {
                    IconButton(onClick = { /* Filter */ }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                    IconButton(onClick = { /* Download */ }) {
                        Icon(Icons.Default.GetApp, contentDescription = "Download")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Month Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.onEvent(HistoryEvent.PreviousMonth) }) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Month")
                }
                Text(
                    text = state.selectedMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = { viewModel.onEvent(HistoryEvent.NextMonth) },
                    enabled = state.selectedMonth.plusMonths(1).isBefore(java.time.YearMonth.now().plusMonths(1))
                ) {
                    Icon(Icons.Default.ChevronRight, contentDescription = "Next Month")
                }
            }

            // Summary Stats Row
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { StatCard("Present", state.presentDays, StatusPresent) }
                item { StatCard("Late", state.lateDays, StatusLate) }
                item { StatCard("Absent", state.absentDays, StatusAbsent) }
                item { StatCard("Leave", state.leaveDays, StatusOnLeave) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // View Toggle
            TabRow(
                selectedTabIndex = if (state.isCalendarView) 0 else 1,
                containerColor = Color.Transparent
            ) {
                Tab(
                    selected = state.isCalendarView,
                    onClick = { if (!state.isCalendarView) viewModel.onEvent(HistoryEvent.ToggleViewMode) },
                    text = { Text("Calendar") }
                )
                Tab(
                    selected = !state.isCalendarView,
                    onClick = { if (state.isCalendarView) viewModel.onEvent(HistoryEvent.ToggleViewMode) },
                    text = { Text("List") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content
            if (state.isCalendarView) {
                CalendarView(
                    yearMonth = state.selectedMonth,
                    attendanceList = state.attendanceList,
                    onDateSelected = { date ->
                        viewModel.onEvent(HistoryEvent.DateSelected(date))
                        showBottomSheet = true
                    }
                )
            } else {
                ListView(
                    attendanceList = state.attendanceList,
                    onItemClick = { attendance ->
                        viewModel.onEvent(HistoryEvent.DateSelected(attendance.date.toLocalDate()))
                        showBottomSheet = true
                    }
                )
            }
        }
    }
}

@Composable
fun StatCard(label: String, count: Int, color: Color) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun CalendarView(
    yearMonth: java.time.YearMonth,
    attendanceList: List<Attendance>,
    onDateSelected: (LocalDate) -> Unit
) {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value // 1 (Mon) to 7 (Sun)
    val days = (1..daysInMonth).toList()

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Weekday Headers
        items(listOf("M", "T", "W", "T", "F", "S", "S")) { day ->
            Text(
                text = day,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        // Empty cells for offset
        items(firstDayOfWeek - 1) {
            Spacer(modifier = Modifier.size(40.dp))
        }

        // Days
        items(days) { day ->
            val date = yearMonth.atDay(day)
            val attendance = attendanceList.find { it.date.toLocalDate() == date }
            val statusColor = when (attendance?.status) {
                AttendanceStatus.PRESENT -> StatusPresent
                AttendanceStatus.LATE -> StatusLate
                AttendanceStatus.ABSENT -> StatusAbsent
                AttendanceStatus.ON_LEAVE -> StatusOnLeave
                AttendanceStatus.HOLIDAY -> StatusHoliday
                else -> null
            }

            Column(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onDateSelected(date) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = day.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (date == LocalDate.now()) FontWeight.Bold else FontWeight.Normal,
                    color = if (date == LocalDate.now()) Primary else MaterialTheme.colorScheme.onSurface
                )
                if (statusColor != null) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(statusColor)
                    )
                }
            }
        }
    }
}

@Composable
fun ListView(
    attendanceList: List<Attendance>,
    onItemClick: (Attendance) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(attendanceList.sortedByDescending { it.date }) { attendance ->
            AttendanceListItem(attendance, onItemClick)
        }
    }
}

@Composable
fun AttendanceListItem(
    attendance: Attendance,
    onClick: (Attendance) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(attendance) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date Column
            Column(
                modifier = Modifier.width(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = attendance.date.month.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    style = MaterialTheme.typography.labelSmall,
                    color = Primary
                )
                Text(
                    text = attendance.date.dayOfMonth.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Details Column
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusChip(attendance.status)
                    Spacer(modifier = Modifier.width(8.dp))
                    if (attendance.status == AttendanceStatus.LATE) {
                        Text(
                            text = "Late by ${attendance.lateMinutes}m",
                            style = MaterialTheme.typography.labelSmall,
                            color = StatusLate
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${attendance.checkInTime?.format(DateTimeFormatter.ofPattern("hh:mm a")) ?: "--:--"} → ${attendance.checkOutTime?.format(DateTimeFormatter.ofPattern("hh:mm a")) ?: "--:--"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${attendance.hoursWorked.toInt()}h ${(attendance.hoursWorked % 1 * 60).toInt()}m worked",
                    style = MaterialTheme.typography.bodySmall,
                    color = Secondary
                )
            }

            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

@Composable
fun StatusChip(status: AttendanceStatus) {
    val (color, text) = when (status) {
        AttendanceStatus.PRESENT -> StatusPresent to "Present"
        AttendanceStatus.LATE -> StatusLate to "Late"
        AttendanceStatus.ABSENT -> StatusAbsent to "Absent"
        AttendanceStatus.ON_LEAVE -> StatusOnLeave to "Leave"
        else -> Color.Gray to status.name
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(4.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

@Composable
fun DayDetailContent(date: LocalDate, attendance: Attendance?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .padding(bottom = 32.dp)
    ) {
        Text(
            text = date.format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (attendance != null) {
            // Check In
            TimelineItem(
                time = attendance.checkInTime?.format(DateTimeFormatter.ofPattern("hh:mm a")) ?: "--:--",
                label = "Check In",
                location = "Office - Tech Park",
                color = SecondaryContainer
            )

            // Dashed Line
            Box(
                modifier = Modifier
                    .padding(start = 28.dp)
                    .height(32.dp)
                    .width(2.dp)
                    .background(Color.LightGray) // Should be dashed
            )

            // Check Out
            TimelineItem(
                time = attendance.checkOutTime?.format(DateTimeFormatter.ofPattern("hh:mm a")) ?: "--:--",
                label = "Check Out",
                location = "Office - Tech Park",
                color = if (attendance.earlyDepartureMinutes > 0) Error else SecondaryContainer
            )

            Spacer(modifier = Modifier.height(24.dp))

            Divider()

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Total Hours", style = MaterialTheme.typography.labelMedium)
                    Text("${attendance.hoursWorked}h", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("Break", style = MaterialTheme.typography.labelMedium)
                    Text("${attendance.breakMinutes}m", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("Overtime", style = MaterialTheme.typography.labelMedium)
                    Text("${attendance.overtimeHours}h", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            Text("No attendance record for this date.")
        }
    }
}

@Composable
fun TimelineItem(time: String, label: String, location: String, color: Color) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(time, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                Text(location, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}
