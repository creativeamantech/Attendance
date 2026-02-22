package com.company.attendanceapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.attendanceapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
    // private val userPreferences: UserPreferencesRepository // TODO: Check onboarding status
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        checkAppState()
    }

    private fun checkAppState() {
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()

            val isAuthenticated = authRepository.isUserLoggedIn()
            val isOnboardingCompleted = true // TODO: Fetch from DataStore

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
