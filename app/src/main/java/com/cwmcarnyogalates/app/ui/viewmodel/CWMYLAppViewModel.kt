package com.cwmcarnyogalates.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cwmcarnyogalates.app.data.datastore.ThemeManager
import com.cwmcarnyogalates.app.data.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CWMYLAppViewModel(
    userRepository: UserRepository,
    themeManager: ThemeManager,
) : ViewModel() {

    val userNameState: StateFlow<String> =
        userRepository.observeUserName()
            .map { it ?: "Гость" }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "Гость")

    val isDarkTheme: StateFlow<Boolean> =
        themeManager.observeDarkTheme()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)
}
