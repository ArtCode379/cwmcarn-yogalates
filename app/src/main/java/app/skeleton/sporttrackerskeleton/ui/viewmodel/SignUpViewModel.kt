package app.skeleton.sporttrackerskeleton.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.skeleton.sporttrackerskeleton.data.model.UserModel
import app.skeleton.sporttrackerskeleton.data.repository.UserRepository
import app.skeleton.sporttrackerskeleton.ui.state.DataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _userNameErrorState = MutableStateFlow(false)
    val userNameErrorState: StateFlow<Boolean> = _userNameErrorState.asStateFlow()

    private val _userSignedUpState = MutableStateFlow<DataUiState<Unit>>(DataUiState.Initial)
    val userSignedUpState: StateFlow<DataUiState<Unit>> = _userSignedUpState.asStateFlow()

    var userName by mutableStateOf("")
        private set

    var userHeight by mutableStateOf("")
        private set

    var userWeight by mutableStateOf("")
        private set

    fun updateUserName(input: String) {
        _userNameErrorState.value = false
        userName = input
    }

    fun updateUserHeight(input: String) {
        userHeight = input
    }

    fun updateUserWeight(input: String) {
        userWeight = input
    }

    fun signUpUser() {
        if (userName.isBlank()) {
            _userNameErrorState.value = true
            return
        }

        val height = userHeight.toIntOrNull() ?: 0
        val weight = userWeight.toFloatOrNull() ?: 0f

        val user = UserModel(
            name = userName,
            height = height,
            weight = weight,
            age = 0,
            goalWorkouts = 0
        )

        viewModelScope.launch {
            userRepository.saveUser(user)
            _userSignedUpState.update { DataUiState.Data<Unit>(Unit) }
        }
    }
}