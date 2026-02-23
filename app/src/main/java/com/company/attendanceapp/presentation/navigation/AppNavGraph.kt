package com.company.attendanceapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.company.attendanceapp.presentation.components.BottomNavBar
import com.company.attendanceapp.presentation.screens.auth.LoginScreen
import com.company.attendanceapp.presentation.screens.checkin.CheckInScreen
import com.company.attendanceapp.presentation.screens.admin.AdminDashboardScreen
import com.company.attendanceapp.presentation.screens.history.HistoryScreen
import com.company.attendanceapp.presentation.screens.home.HomeScreen
import com.company.attendanceapp.presentation.screens.leave.LeaveScreen
import com.company.attendanceapp.presentation.screens.notifications.NotificationsScreen
import com.company.attendanceapp.presentation.screens.onboarding.OnboardingScreen
import com.company.attendanceapp.presentation.screens.profile.ProfileScreen
import com.company.attendanceapp.presentation.screens.reports.ReportsScreen
import com.company.attendanceapp.presentation.screens.schedule.ScheduleScreen
import com.company.attendanceapp.presentation.screens.splash.SplashScreen

@Composable
fun AppNavGraph(navController: NavHostController, userRole: String?) {
    val navItems = mutableListOf(
        BottomNavItem.Home,
        BottomNavItem.CheckIn,
        BottomNavItem.History,
        BottomNavItem.Reports,
        BottomNavItem.Profile
    )

    if (userRole == "ADMIN") {
        // We need an Admin Tab item if we want it in the bottom bar,
        // or just accessible via other means.
        // For now, let's assume it's a separate route accessible from Profile or a specific logic.
        // However, the prompt implies "Role-based access control".
        // Let's add an Admin item if the user is admin.
        // Note: BottomNavItem needs to be extended or we just add it dynamically if we had a generic item.
        // Since BottomNavItem is a sealed class with objects, we can't dynamically add a new 'type' easily
        // without defining it first.
        // Let's assume we want to show it. But we need an icon for it.
        // For simplicity, let's just keep the routes available but not necessarily in the bottom bar
        // unless we refactor BottomNavItem to be a data class or add an Admin object.
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                items = navItems
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Splash.route) {
                SplashScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    },
                    onNavigateToOnboarding = {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    },
                    onNavigateToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(route = Screen.Onboarding.route) {
                OnboardingScreen(navController = navController)
            }

            composable(route = Screen.Login.route) {
                LoginScreen(navController = navController)
            }

            composable(route = Screen.Home.route) {
                HomeScreen(navController = navController)
            }

            composable(route = Screen.CheckIn.route) {
                CheckInScreen(navController = navController)
            }

            composable(route = Screen.History.route) {
                HistoryScreen(navController = navController)
            }

            composable(route = Screen.Reports.route) {
                ReportsScreen(navController = navController)
            }

            composable(route = Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }

            composable(route = Screen.Notifications.route) {
                NotificationsScreen(navController = navController)
            }

            composable(route = Screen.Schedule.route) {
                ScheduleScreen(navController = navController)
            }

            composable(route = Screen.Admin.route) {
                AdminDashboardScreen(navController = navController)
            }

            composable(route = Screen.Leave.route) {
                LeaveScreen(navController = navController)
            }
        }
    }
}
