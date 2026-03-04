package app.skeleton.sporttrackerskeleton.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.skeleton.sporttrackerskeleton.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _shouldNavigateToWorkout = MutableStateFlow<Boolean?>(null)
    val shouldNavigateToWorkout: StateFlow<Boolean?> = _shouldNavigateToWorkout.asStateFlow()

    init {
        checkUserExists()
    }

    private fun checkUserExists() {
        viewModelScope.launch {
            val user = userRepository.observeUser().firstOrNull()
            _shouldNavigateToWorkout.value = user != null
        }
    }
}
