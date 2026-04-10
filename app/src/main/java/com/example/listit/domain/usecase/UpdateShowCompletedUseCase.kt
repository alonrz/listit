package com.example.listit.domain.usecase

import com.example.listit.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateShowCompletedUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(show: Boolean) = repository.setShowCompletedItems(show)
}
