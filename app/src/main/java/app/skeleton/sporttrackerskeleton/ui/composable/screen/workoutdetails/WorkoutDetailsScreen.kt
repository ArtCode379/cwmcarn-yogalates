package app.skeleton.sporttrackerskeleton.ui.composable.screen.workoutdetails

import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.skeleton.sporttrackerskeleton.R
import app.skeleton.sporttrackerskeleton.data.model.ExerciseModel
import app.skeleton.sporttrackerskeleton.ui.composable.shared.NoDataContent
import app.skeleton.sporttrackerskeleton.ui.state.DataUiState
import app.skeleton.sporttrackerskeleton.ui.viewmodel.WorkoutDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WorkoutDetailsScreen(
    workoutId: Int,
    modifier: Modifier = Modifier,
    viewModel: WorkoutDetailsViewModel = koinViewModel()
) {
    val exercisesState by viewModel.exercisesState.collectAsState()

    LaunchedEffect(workoutId) {
        viewModel.loadExercises(workoutId)
    }

    WorkoutDetailsScreenContent(
        modifier = modifier,
        exercisesState = exercisesState
    )
}

@Composable
private fun WorkoutDetailsScreenContent(
    modifier: Modifier = Modifier,
    exercisesState: DataUiState<List<ExerciseModel>>,
) {
    when (exercisesState) {
        is DataUiState.Data<List<ExerciseModel>> -> {
            WorkoutDetailsDataContent(
                exercises = exercisesState.data,
                modifier = modifier
            )
        }

        DataUiState.Empty -> {
            NoDataContent(
                modifier = modifier,
                noContentMainText = {
                    Text(
                        text = stringResource(R.string.no_exercises_info),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        }

        DataUiState.Initial -> Unit
    }
}

@Composable
private fun WorkoutDetailsDataContent(
    exercises: List<ExerciseModel>,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        ExercisesList(
            exercises = exercises,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ExercisesList(
    exercises: List<ExerciseModel>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    overscrollEffect: OverscrollEffect? = rememberOverscrollEffect(),
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        overscrollEffect = overscrollEffect,
    ) {
        items(
            items = exercises,
            key = { exercise ->
                exercise.name
            }
        ) { exercise ->
            // ExerciseItem(exercise)
            Unit
        }
    }
}