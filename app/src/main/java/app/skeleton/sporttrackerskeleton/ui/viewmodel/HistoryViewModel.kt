package app.skeleton.sporttrackerskeleton.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.skeleton.sporttrackerskeleton.data.model.CompleteWorkoutModel
import app.skeleton.sporttrackerskeleton.data.repository.CompleteWorkoutRepository
import app.skeleton.sporttrackerskeleton.data.util.CalendarHelper
import app.skeleton.sporttrackerskeleton.ui.state.DataUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

typealias CompleteWorkoutsState = DataUiState<List<CompleteWorkoutModel>>

class HistoryViewModel(
    private val completeWorkoutRepository: CompleteWorkoutRepository
) : ViewModel() {

    private val _viewedMonth = MutableStateFlow(LocalDate.now().withDayOfMonth(1))
    val viewedMonth: StateFlow<LocalDate> = _viewedMonth.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    val calendarDays: StateFlow<List<LocalDate?>> = _viewedMonth
        .combine(_selectedDate) { month, _ ->
            CalendarHelper.getDaysInMonth(month)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _completeWorkoutsState =
        MutableStateFlow<CompleteWorkoutsState>(DataUiState.Initial)
    val completeWorkoutsState: StateFlow<CompleteWorkoutsState> =
        _completeWorkoutsState.asStateFlow()

    init {
        observeCompleteWorkoutsByDate(_selectedDate.value)
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        observeCompleteWorkoutsByDate(date)
    }

    fun nextMonth() {
        _viewedMonth.update { CalendarHelper.getNextMonth(it) }
    }

    fun previousMonth() {
        _viewedMonth.update { CalendarHelper.getPreviousMonth(it) }
    }

    private fun observeCompleteWorkoutsByDate(date: LocalDate) {
        viewModelScope.launch {
            completeWorkoutRepository.observeAllByDate(date)
                .collect { workouts ->
                    _completeWorkoutsState.update {
                        if (workouts.isEmpty()) DataUiState.Empty
                        else DataUiState.Data(workouts)
                    }
                }
        }
    }
}