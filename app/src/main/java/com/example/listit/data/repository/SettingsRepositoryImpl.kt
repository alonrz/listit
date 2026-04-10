package com.example.listit.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.listit.domain.model.ThemeMode
import com.example.listit.domain.model.UserSettings
import com.example.listit.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {

    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val USE_DYNAMIC_COLOR = booleanPreferencesKey("use_dynamic_color")
        val SHOW_COMPLETED_ITEMS = booleanPreferencesKey("show_completed_items")
    }

    override fun observeSettings(): Flow<UserSettings> = dataStore.data.map { prefs ->
        val themeMode = prefs[Keys.THEME_MODE]
            ?.let { runCatching { ThemeMode.valueOf(it) }.getOrNull() }
            ?: ThemeMode.SYSTEM
        UserSettings(
            themeMode = themeMode,
            useDynamicColor = prefs[Keys.USE_DYNAMIC_COLOR] ?: true,
            showCompletedItems = prefs[Keys.SHOW_COMPLETED_ITEMS] ?: true,
        )
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.edit { it[Keys.THEME_MODE] = mode.name }
    }

    override suspend fun setUseDynamicColor(enabled: Boolean) {
        dataStore.edit { it[Keys.USE_DYNAMIC_COLOR] = enabled }
    }

    override suspend fun setShowCompletedItems(show: Boolean) {
        dataStore.edit { it[Keys.SHOW_COMPLETED_ITEMS] = show }
    }
}
