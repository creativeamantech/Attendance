package com.company.attendanceapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material.icons.outlined.History as OutlinedHistory
import androidx.compose.material.icons.outlined.Home as OutlinedHome
import androidx.compose.material.icons.outlined.Person as OutlinedPerson
import androidx.compose.material.icons.outlined.QrCode as OutlinedQrCode
import androidx.compose.material.icons.outlined.Summarize as OutlinedSummarize
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Home : BottomNavItem(
        route = Screen.Home.route,
        label = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.OutlinedHome
    )
    object CheckIn : BottomNavItem(
        route = Screen.CheckIn.route,
        label = "Check-In",
        selectedIcon = Icons.Filled.QrCode,
        unselectedIcon = Icons.Outlined.OutlinedQrCode
    )
    object History : BottomNavItem(
        route = Screen.History.route,
        label = "History",
        selectedIcon = Icons.Filled.History,
        unselectedIcon = Icons.Outlined.OutlinedHistory
    )
    object Reports : BottomNavItem(
        route = Screen.Reports.route,
        label = "Reports",
        selectedIcon = Icons.Filled.Summarize,
        unselectedIcon = Icons.Outlined.OutlinedSummarize
    )
    object Profile : BottomNavItem(
        route = Screen.Profile.route,
        label = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.OutlinedPerson
    )
}
