package com.cwmcarnyogalates.app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cwmcarnyogalates.app.data.model.BookingModel
import com.cwmcarnyogalates.app.data.model.BookingStatus
import com.cwmcarnyogalates.app.data.model.WorkoutType
import com.cwmcarnyogalates.app.data.repository.BookingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CWMYLBookingViewModel(
    private val bookingRepository: BookingRepository,
) : ViewModel() {

    var selectedType by mutableStateOf(WorkoutType.HATHA)
        private set
    var selectedDate by mutableStateOf(LocalDate.now().plusDays(1))
        private set
    var selectedTime by mutableStateOf("09:00")
        private set
    var instructorName by mutableStateOf("")
        private set
    var notes by mutableStateOf("")
        private set

    private val _booked = MutableStateFlow(false)
    val booked: StateFlow<Boolean> = _booked.asStateFlow()

    val availableTimes = listOf("09:00", "10:30", "12:00", "14:00", "15:30", "17:00", "18:30", "20:00")

    fun selectType(type: WorkoutType) { selectedType = type }
    fun selectDate(date: LocalDate) { selectedDate = date }
    fun selectTime(time: String) { selectedTime = time }
    fun setInstructor(name: String) { instructorName = name }
    fun setNotes(value: String) { notes = value }

    fun book() {
        viewModelScope.launch {
            bookingRepository.insert(
                BookingModel(
                    id = 0,
                    yogaType = selectedType,
                    date = selectedDate,
                    time = selectedTime,
                    instructorName = instructorName,
                    notes = notes,
                    status = BookingStatus.SCHEDULED,
                )
            )
            _booked.value = true
        }
    }

    fun resetBooked() { _booked.value = false }
}
