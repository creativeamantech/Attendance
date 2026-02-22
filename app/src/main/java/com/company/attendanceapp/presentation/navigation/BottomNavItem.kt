package com.company.attendanceapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material.icons.outlined.Summarize
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
        unselectedIcon = Icons.Outlined.Home
    )
    object CheckIn : BottomNavItem(
        route = Screen.CheckIn.route,
        label = "Check-In",
        selectedIcon = Icons.Filled.QrCode,
        unselectedIcon = Icons.Outlined.QrCode
    )
    object History : BottomNavItem(
        route = Screen.History.route,
        label = "History",
        selectedIcon = Icons.Filled.History,
        unselectedIcon = Icons.Outlined.History
    )
    object Reports : BottomNavItem(
        route = Screen.Reports.route,
        label = "Reports",
        selectedIcon = Icons.Filled.Summarize,
        unselectedIcon = Icons.Outlined.Summarize
    )
    object Profile : BottomNavItem(
        route = Screen.Profile.route,
        label = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
}
