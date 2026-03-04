package app.skeleton.sporttrackerskeleton.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.skeleton.sporttrackerskeleton.data.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AppViewModel(userRepository: UserRepository) : ViewModel() {

    val userNameState: StateFlow<String> =
        userRepository.observeUserName()
            .map { name -> name ?: "Guest" }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = "Guest"
            )
}