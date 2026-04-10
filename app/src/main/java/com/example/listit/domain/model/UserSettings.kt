package com.example.listit.domain.model

data class UserSettings(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val useDynamicColor: Boolean = true,
    val showCompletedItems: Boolean = true,
)
