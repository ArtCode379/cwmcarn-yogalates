package com.cwmcarnyogalates.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cwmcarnyogalates.app.data.model.CompleteWorkoutModel
import com.cwmcarnyogalates.app.data.model.WorkoutType
import com.cwmcarnyogalates.app.data.repository.CompleteWorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

enum class CWMYLTimeFilter { ALL, WEEK, MONTH }

class CWMYLStatisticsViewModel(
    private val completeWorkoutRepository: CompleteWorkoutRepository,
) : ViewModel() {

    private val _allWorkouts: StateFlow<List<CompleteWorkoutModel>> =
        completeWorkoutRepository.observeAll()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val timeFilter = MutableStateFlow(CWMYLTimeFilter.ALL)
    val typeFilter = MutableStateFlow<WorkoutType?>(null)

    val filteredWorkouts: StateFlow<List<CompleteWorkoutModel>> =
        combine(_allWorkouts, timeFilter, typeFilter) { workouts, time, type ->
            val today = LocalDate.now()
            val timeFiltered = when (time) {
                CWMYLTimeFilter.ALL -> workouts
                CWMYLTimeFilter.WEEK -> workouts.filter { it.date >= today.minusDays(6) }
                CWMYLTimeFilter.MONTH -> workouts.filter { it.date >= today.withDayOfMonth(1) }
            }
            if (type == null) timeFiltered else timeFiltered.filter { it.workoutType == type }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun longestStreak(workouts: List<CompleteWorkoutModel>): Int {
        if (workouts.isEmpty()) return 0
        val dates = workouts.map { it.date }.distinct().sorted()
        var maxStreak = 1
        var current = 1
        for (i in 1 until dates.size) {
            if (dates[i] == dates[i - 1].plusDays(1)) {
                current++
                if (current > maxStreak) maxStreak = current
            } else {
                current = 1
            }
        }
        return maxStreak
    }

    fun countByType(workouts: List<CompleteWorkoutModel>): Map<WorkoutType, Int> =
        WorkoutType.values().associateWith { type -> workouts.count { it.workoutType == type } }
}
