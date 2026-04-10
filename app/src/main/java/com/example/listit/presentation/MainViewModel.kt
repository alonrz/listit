package com.example.listit.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listit.domain.model.ThemeMode
import com.example.listit.domain.usecase.ObserveSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * UI state controlling app-wide theming, derived from persisted user settings.
 */
data class ThemeUiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val useDynamicColor: Boolean = true,
)

/**
 * Holds theme state for the app's root composable. Eagerly collects so the correct
 * theme is in place before the first frame, avoiding a flash on cold start.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    observeSettingsUseCase: ObserveSettingsUseCase,
) : ViewModel() {

    val themeState: StateFlow<ThemeUiState> = observeSettingsUseCase()
        .map { ThemeUiState(themeMode = it.themeMode, useDynamicColor = it.useDynamicColor) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ThemeUiState(),
        )
}
