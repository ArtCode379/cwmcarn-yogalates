package app.skeleton.sporttrackerskeleton.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.skeleton.sporttrackerskeleton.data.model.ExerciseModel
import app.skeleton.sporttrackerskeleton.data.repository.StatisticsRepository
import app.skeleton.sporttrackerskeleton.data.repository.WorkoutRepository
import app.skeleton.sporttrackerskeleton.ui.state.DataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutDetailsViewModel(
    private val workoutRepository: WorkoutRepository,
    private val statisticsRepository: StatisticsRepository,
) : ViewModel() {
    private val _exercisesState =
        MutableStateFlow<DataUiState<List<ExerciseModel>>>(DataUiState.Empty)
    val exercisesState: StateFlow<DataUiState<List<ExerciseModel>>> = _exercisesState.asStateFlow()

    fun loadExercises(workoutId: Int) {
        viewModelScope.launch {
            _exercisesState.update {
                val exercises = workoutRepository
                    .getById(workoutId)
                    ?.exercises

                if (exercises.isNullOrEmpty()) {
                    DataUiState.Empty
                } else {
                    DataUiState.Data(exercises)
                }
            }
        }
    }
}