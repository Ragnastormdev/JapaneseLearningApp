package com.ragnastormdev.japaneselearningapp.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.themeDataStore by preferencesDataStore(
    name = "theme_preferences"
)

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

@Singleton
class ThemePreference @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private companion object {
        val THEME_MODE_KEY: Preferences.Key<String> =
            stringPreferencesKey("theme_mode")
    }

    val themeMode: Flow<ThemeMode> =
        context.themeDataStore.data.map { preferences ->

            val savedThemeMode = preferences[THEME_MODE_KEY]

            when (savedThemeMode) {
                ThemeMode.LIGHT.name -> ThemeMode.LIGHT
                ThemeMode.DARK.name -> ThemeMode.DARK
                else -> ThemeMode.SYSTEM
            }
        }

    suspend fun saveThemeMode(themeMode: ThemeMode) {
        context.themeDataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode.name
        }
    }
}