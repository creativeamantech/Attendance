package com.company.attendanceapp.core.security

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    val authToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN_KEY]
    }

    val userId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_ID_KEY]
    }

    val userRole: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_ROLE_KEY]
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    suspend fun saveUserId(id: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id
        }
    }

    suspend fun saveUserRole(role: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ROLE_KEY] = role
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
