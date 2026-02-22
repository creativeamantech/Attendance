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
fun AppNavGraph(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                items = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.CheckIn,
                    BottomNavItem.History,
                    BottomNavItem.Reports,
                    BottomNavItem.Profile
                )
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
