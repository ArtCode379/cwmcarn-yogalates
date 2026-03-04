package com.cwmcarnyogalates.app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cwmcarnyogalates.app.data.model.CompleteWorkoutModel
import com.cwmcarnyogalates.app.data.model.WorkoutType
import com.cwmcarnyogalates.app.data.repository.CompleteWorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CWMYLWorkoutViewModel(
    private val completeWorkoutRepository: CompleteWorkoutRepository,
) : ViewModel() {

    var selectedType by mutableStateOf(WorkoutType.HATHA)
        private set

    var durationMinutes by mutableIntStateOf(60)
        private set

    var selectedDate by mutableStateOf(LocalDate.now())
        private set

    var notes by mutableStateOf("")
        private set

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved.asStateFlow()

    fun selectType(type: WorkoutType) { selectedType = type }
    fun setDuration(minutes: Int) { durationMinutes = minutes }
    fun setDate(date: LocalDate) { selectedDate = date }
    fun setNotes(value: String) { notes = value }

    fun save() {
        viewModelScope.launch {
            completeWorkoutRepository.save(
                CompleteWorkoutModel(
                    id = 0,
                    workoutType = selectedType,
                    durationMinutes = durationMinutes,
                    date = selectedDate,
                    notes = notes,
                )
            )
            _saved.value = true
        }
    }

    fun resetSaved() { _saved.value = false }
}
