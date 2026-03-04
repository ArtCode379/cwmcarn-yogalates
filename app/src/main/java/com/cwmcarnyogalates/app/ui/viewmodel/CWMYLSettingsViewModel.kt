package com.cwmcarnyogalates.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cwmcarnyogalates.app.data.datastore.ThemeManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CWMYLSettingsViewModel(
    private val themeManager: ThemeManager,
) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean> =
        themeManager.observeDarkTheme()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    fun toggleTheme(enabled: Boolean) {
        viewModelScope.launch { themeManager.setDarkTheme(enabled) }
    }
}
