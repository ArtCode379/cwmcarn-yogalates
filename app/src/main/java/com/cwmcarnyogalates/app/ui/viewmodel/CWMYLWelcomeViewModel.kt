package com.cwmcarnyogalates.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cwmcarnyogalates.app.data.model.BookingModel
import com.cwmcarnyogalates.app.data.model.CompleteWorkoutModel
import com.cwmcarnyogalates.app.data.repository.BookingRepository
import com.cwmcarnyogalates.app.data.repository.CompleteWorkoutRepository
import com.cwmcarnyogalates.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class CWMYLWelcomeViewModel(
    private val userRepository: UserRepository,
    private val completeWorkoutRepository: CompleteWorkoutRepository,
    private val bookingRepository: BookingRepository,
) : ViewModel() {

    private val _navigateToSignUp = MutableStateFlow<Boolean?>(null)
    val navigateToSignUp: StateFlow<Boolean?> = _navigateToSignUp.asStateFlow()

    val upcomingBookings: StateFlow<List<BookingModel>> =
        bookingRepository.observeUpcoming()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val weeklyWorkouts: StateFlow<List<CompleteWorkoutModel>> =
        completeWorkoutRepository.observeAll()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        checkUser()
    }

    private fun checkUser() {
        viewModelScope.launch {
            val user = userRepository.observeUser().firstOrNull()
            _navigateToSignUp.value = user == null
        }
    }

    fun getThisWeekCount(workouts: List<CompleteWorkoutModel>): Int {
        val weekAgo = LocalDate.now().minusDays(6)
        return workouts.count { it.date >= weekAgo }
    }
}
