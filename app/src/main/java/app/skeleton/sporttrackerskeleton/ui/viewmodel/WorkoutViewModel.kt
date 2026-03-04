package app.skeleton.sporttrackerskeleton.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.skeleton.sporttrackerskeleton.data.model.WorkoutModel
import app.skeleton.sporttrackerskeleton.data.repository.WorkoutRepository
import app.skeleton.sporttrackerskeleton.ui.state.DataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

typealias WorkoutsState = DataUiState<List<WorkoutModel>>

class WorkoutViewModel(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {
    private val _workoutsStateState = MutableStateFlow<WorkoutsState>(DataUiState.Initial)
    val workoutsState: StateFlow<WorkoutsState> = _workoutsStateState.asStateFlow()

    init {
        loadWorkouts()
    }

    private fun loadWorkouts() {
        viewModelScope.launch {
            workoutRepository.observeAll().collect { workouts ->
                _workoutsStateState.update {
                    if (workouts.isEmpty()) {
                        DataUiState.Empty
                    } else {
                        DataUiState.Data(workouts)
                    }
                }
            }
        }
    }
}