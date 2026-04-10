package com.example.listit.domain.usecase

import com.example.listit.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateDynamicColorUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(enabled: Boolean) = repository.setUseDynamicColor(enabled)
}
