package app.skeleton.sporttrackerskeleton.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.skeleton.sporttrackerskeleton.data.model.UserModel
import app.skeleton.sporttrackerskeleton.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userState = MutableStateFlow<UserModel?>(null)

    private val _userNameErrorState = MutableStateFlow(false)
    val userNameErrorState: StateFlow<Boolean> = _userNameErrorState.asStateFlow()

    var userName by mutableStateOf("")
        private set

    var userHeight by mutableStateOf("")
        private set

    var userWeight by mutableStateOf("")
        private set

    var userAge by mutableStateOf("")
        private set

    var userGoalWorkouts by mutableStateOf("")
        private set

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            userRepository.observeUser().collect { user ->
                _userState.update { user }

                user?.let {
                    userName = it.name
                    userHeight = if (it.height != 0) it.height.toString() else ""
                    userWeight = if (it.weight != 0f) it.weight.toString() else ""
                    userAge = if (it.age != 0) it.age.toString() else ""
                    userGoalWorkouts = if (it.goalWorkouts != 0) it.goalWorkouts.toString() else ""
                }
            }
        }
    }

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

    fun updateUserAge(input: String) {
        userAge = input
    }

    fun updateUserGoalWorkouts(input: String) {
        userGoalWorkouts = input
    }

    fun updateUser() {
        if (userName.isBlank()) {
            _userNameErrorState.value = true
            return
        }

        val height = userHeight.toIntOrNull() ?: 0
        val weight = userWeight.toFloatOrNull() ?: 0f
        val age = userAge.toIntOrNull() ?: 0
        val goal = userGoalWorkouts.toIntOrNull() ?: 0

        val user = UserModel(
            name = userName,
            height = height,
            weight = weight,
            age = age,
            goalWorkouts = goal
        )

        viewModelScope.launch {
            userRepository.saveUser(user)
        }
    }
}