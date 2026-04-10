package com.example.listit.domain.usecase

import com.example.listit.domain.model.ThemeMode
import com.example.listit.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateThemeModeUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(mode: ThemeMode) = repository.setThemeMode(mode)
}
