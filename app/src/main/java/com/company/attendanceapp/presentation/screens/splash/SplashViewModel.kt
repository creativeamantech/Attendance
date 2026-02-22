package com.company.attendanceapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    // private val authRepository: AuthRepository, // TODO
    // private val userPreferences: UserPreferencesRepository // TODO
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        checkAppState()
    }

    private fun checkAppState() {
        viewModelScope.launch {
            // Simulate checks
            val startTime = System.currentTimeMillis()

            // TODO: Check actual auth state
            val isAuthenticated = false
            val isOnboardingCompleted = false

            // Ensure minimum display duration of 2000ms
            val elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime < 2000) {
                delay(2000 - elapsedTime)
            }

            if (isAuthenticated) {
                _uiState.value = SplashUiState.NavigateToHome
            } else if (isOnboardingCompleted) {
                _uiState.value = SplashUiState.NavigateToLogin
            } else {
                _uiState.value = SplashUiState.NavigateToOnboarding
            }
        }
    }
}

sealed class SplashUiState {
    object Loading : SplashUiState()
    object NavigateToHome : SplashUiState()
    object NavigateToLogin : SplashUiState()
    object NavigateToOnboarding : SplashUiState()
}
