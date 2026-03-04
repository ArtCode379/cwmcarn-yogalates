package com.cwmcarnyogalates.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cwmcarnyogalates.app.data.model.CompleteWorkoutModel
import com.cwmcarnyogalates.app.data.repository.CompleteWorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CWMYLHistoryViewModel(
    private val completeWorkoutRepository: CompleteWorkoutRepository,
) : ViewModel() {

    val allWorkouts: StateFlow<List<CompleteWorkoutModel>> =
        completeWorkoutRepository.observeAll()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            // trigger re-collection by re-subscribing; Room Flow auto-updates on DB change
            _isRefreshing.value = false
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch { completeWorkoutRepository.deleteById(id) }
    }
}
