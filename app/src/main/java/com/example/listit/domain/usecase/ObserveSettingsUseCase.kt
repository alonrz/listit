package com.example.listit.domain.usecase

import com.example.listit.domain.model.UserSettings
import com.example.listit.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): Flow<UserSettings> = repository.observeSettings()
}
