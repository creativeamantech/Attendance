package com.company.attendanceapp.presentation.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
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
import com.company.attendanceapp.presentation.theme.Secondary
import com.company.attendanceapp.presentation.theme.StatusPresent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                actions = {
                    TextButton(onClick = { /* Mark all read */ }) {
                        Text("Mark All Read")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ScrollableTabRow(
                selectedTabIndex = state.selectedFilter.ordinal,
                containerColor = Color.Transparent,
                edgePadding = 16.dp
            ) {
                NotificationFilter.values().forEach { filter ->
                    Tab(
                        selected = state.selectedFilter == filter,
                        onClick = { viewModel.onEvent(NotificationEvent.FilterSelected(filter)) },
                        text = { Text(filter.name) }
                    )
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.filteredNotifications) { item ->
                    NotificationListItem(item)
                }
            }
        }
    }
}

@Composable
fun NotificationListItem(item: NotificationItem) {
    val icon = when (item.type) {
        NotificationType.ALERT -> Icons.Default.Warning
        NotificationType.APPROVAL -> Icons.Default.CheckCircle
        NotificationType.REMINDER -> Icons.Default.Notifications
        NotificationType.SYSTEM -> Icons.Default.Info
    }

    val color = when (item.type) {
        NotificationType.ALERT -> Error
        NotificationType.APPROVAL -> StatusPresent
        NotificationType.REMINDER -> Primary
        NotificationType.SYSTEM -> Color.Gray
    }

    ListItem(
        headlineContent = {
            Text(
                item.title,
                fontWeight = if (item.isRead) FontWeight.Normal else FontWeight.Bold
            )
        },
        supportingContent = {
            Column {
                Text(item.body, maxLines = 2)
                Text(item.time, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color)
            }
        },
        colors = ListItemDefaults.colors(
            containerColor = if (item.isRead) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    )
}
