package com.example.listit.domain.repository

import com.example.listit.domain.model.ThemeMode
import com.example.listit.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeSettings(): Flow<UserSettings>
    suspend fun setThemeMode(mode: ThemeMode)
    suspend fun setUseDynamicColor(enabled: Boolean)
    suspend fun setShowCompletedItems(show: Boolean)
}
