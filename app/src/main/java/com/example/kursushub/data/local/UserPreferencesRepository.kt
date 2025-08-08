package com.example.kursushub.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferencesRepository(private val context: Context) {

    private val uidKey = stringPreferencesKey("user_uid")
    private val isLoggedInKey = booleanPreferencesKey("is_logged_in")

    suspend fun saveUserSession(uid: String) {
        context.dataStore.edit { preferences ->
            preferences[uidKey] = uid
            preferences[isLoggedInKey] = true
        }
    }

    fun getUserSession(): Flow<Pair<String?, Boolean>> {
        return context.dataStore.data.map { preferences ->
            val uid = preferences[uidKey]
            val isLoggedIn = preferences[isLoggedInKey] ?: false
            Pair(uid, isLoggedIn)
        }
    }

    suspend fun clearUserSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}