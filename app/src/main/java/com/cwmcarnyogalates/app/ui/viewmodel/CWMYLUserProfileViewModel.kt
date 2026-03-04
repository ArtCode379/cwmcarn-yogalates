package com.cwmcarnyogalates.app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cwmcarnyogalates.app.data.model.BookingModel
import com.cwmcarnyogalates.app.data.model.UserModel
import com.cwmcarnyogalates.app.data.repository.BookingRepository
import com.cwmcarnyogalates.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CWMYLUserProfileViewModel(
    private val userRepository: UserRepository,
    private val bookingRepository: BookingRepository,
) : ViewModel() {

    private val _user = MutableStateFlow<UserModel?>(null)
    val user: StateFlow<UserModel?> = _user.asStateFlow()

    var userName by mutableStateOf("")
        private set
    var userEmail by mutableStateOf("")
        private set

    private val _nameError = MutableStateFlow(false)
    val nameError: StateFlow<Boolean> = _nameError.asStateFlow()

    val upcomingBookings: StateFlow<List<BookingModel>> =
        bookingRepository.observeUpcoming()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch {
            userRepository.observeUser().collect { u ->
                _user.update { u }
                u?.let {
                    userName = it.name
                    userEmail = it.email
                }
            }
        }
    }

    fun updateName(value: String) { _nameError.value = false; userName = value }
    fun updateEmail(value: String) { userEmail = value }

    fun save() {
        if (userName.isBlank()) { _nameError.value = true; return }
        viewModelScope.launch {
            userRepository.saveUser(
                (_user.value ?: UserModel(name = userName)).copy(name = userName, email = userEmail)
            )
        }
    }
}
