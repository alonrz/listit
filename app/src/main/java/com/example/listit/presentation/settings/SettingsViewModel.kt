package com.example.listit.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listit.domain.model.ThemeMode
import com.example.listit.domain.model.UserSettings
import com.example.listit.domain.usecase.ObserveSettingsUseCase
import com.example.listit.domain.usecase.UpdateDynamicColorUseCase
import com.example.listit.domain.usecase.UpdateShowCompletedUseCase
import com.example.listit.domain.usecase.UpdateThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    observeSettingsUseCase: ObserveSettingsUseCase,
    private val updateThemeModeUseCase: UpdateThemeModeUseCase,
    private val updateDynamicColorUseCase: UpdateDynamicColorUseCase,
    private val updateShowCompletedUseCase: UpdateShowCompletedUseCase,
) : ViewModel() {

    val settings: StateFlow<UserSettings> = observeSettingsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings(),
        )

    fun onThemeModeChanged(mode: ThemeMode) {
        viewModelScope.launch { updateThemeModeUseCase(mode) }
    }

    fun onDynamicColorChanged(enabled: Boolean) {
        viewModelScope.launch { updateDynamicColorUseCase(enabled) }
    }

    fun onShowCompletedChanged(show: Boolean) {
        viewModelScope.launch { updateShowCompletedUseCase(show) }
    }
}
