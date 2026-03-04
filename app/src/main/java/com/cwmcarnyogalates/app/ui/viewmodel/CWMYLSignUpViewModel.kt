package com.cwmcarnyogalates.app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cwmcarnyogalates.app.data.model.UserModel
import com.cwmcarnyogalates.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CWMYLSignUpViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    var userName by mutableStateOf("")
        private set

    private val _nameError = MutableStateFlow(false)
    val nameError: StateFlow<Boolean> = _nameError.asStateFlow()

    private val _signedUp = MutableStateFlow(false)
    val signedUp: StateFlow<Boolean> = _signedUp.asStateFlow()

    fun updateUserName(value: String) {
        _nameError.value = false
        userName = value
    }

    fun signUp() {
        if (userName.isBlank()) {
            _nameError.value = true
            return
        }
        viewModelScope.launch {
            userRepository.saveUser(UserModel(name = userName))
            _signedUp.value = true
        }
    }
}
