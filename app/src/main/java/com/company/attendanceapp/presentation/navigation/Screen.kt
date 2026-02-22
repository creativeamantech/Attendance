package com.company.attendanceapp.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Onboarding : Screen("onboarding_screen")
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
    object CheckIn : Screen("check_in_screen")
    object History : Screen("history_screen")
    object Schedule : Screen("schedule_screen")
    object Leave : Screen("leave_screen")
    object Reports : Screen("reports_screen")
    object Profile : Screen("profile_screen")
    object Notifications : Screen("notifications_screen")
    object Admin : Screen("admin_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
