package app.skeleton.sporttrackerskeleton.ui.composable.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.skeleton.sporttrackerskeleton.R
import app.skeleton.sporttrackerskeleton.data.model.CompleteWorkoutModel
import app.skeleton.sporttrackerskeleton.ui.composable.shared.CalendarView
import app.skeleton.sporttrackerskeleton.ui.composable.shared.NoDataContent
import app.skeleton.sporttrackerskeleton.ui.state.DataUiState
import app.skeleton.sporttrackerskeleton.ui.viewmodel.HistoryViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = koinViewModel(),
) {
    val viewedMonth by viewModel.viewedMonth.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val calendarDays by viewModel.calendarDays.collectAsState()
    val completeWorkoutsState by viewModel.completeWorkoutsState.collectAsState()

    HistoryScreenContent(
        modifier = modifier,
        viewedMonth = viewedMonth,
        selectedDate = selectedDate,
        calendarDays = calendarDays,
        completeWorkoutsState = completeWorkoutsState,
        onDayClick = viewModel::selectDate,
        onPreviousMonthClick = viewModel::previousMonth,
        onNextMonthClick = viewModel::nextMonth
    )
}

@Composable
private fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    viewedMonth: java.time.LocalDate,
    selectedDate: java.time.LocalDate,
    calendarDays: List<java.time.LocalDate?>,
    completeWorkoutsState: DataUiState<List<CompleteWorkoutModel>>,
    onDayClick: (java.time.LocalDate) -> Unit,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        CalendarView(
            viewedMonth = viewedMonth,
            selectedDate = selectedDate,
            days = calendarDays,
            onDayClick = onDayClick,
            onPreviousMonthClick = onPreviousMonthClick,
            onNextMonthClick = onNextMonthClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Workouts for ${selectedDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        DayWorkoutData(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = completeWorkoutsState
        )
    }
}

@Composable
private fun DayWorkoutData(
    modifier: Modifier = Modifier,
    state: DataUiState<List<CompleteWorkoutModel>>
) {
    Box(modifier = modifier) {
        when (state) {
            is DataUiState.Data -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(state.data) { workout ->
                        WorkoutHistoryItem(workout = workout)
                    }
                }
            }

            is DataUiState.Empty -> {
                NoDataContent(
                    modifier = Modifier.align(Alignment.Center),
                    noContentMainText = {
                        Text(text = stringResource(R.string.no_workouts_for_day_info))
                    }
                )
            }

            else -> Unit
        }
    }
}

@Composable
private fun WorkoutHistoryItem(workout: CompleteWorkoutModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = workout.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${workout.duration.toInt()} min",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = workout.endTimestamp.format(DateTimeFormatter.ofPattern("HH:mm")),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
    }
}