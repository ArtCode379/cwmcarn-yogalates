package app.skeleton.sporttrackerskeleton.ui.composable.screen.workout

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.skeleton.sporttrackerskeleton.R
import app.skeleton.sporttrackerskeleton.data.model.WorkoutModel
import app.skeleton.sporttrackerskeleton.ui.composable.shared.NoDataContent
import app.skeleton.sporttrackerskeleton.ui.state.DataUiState
import app.skeleton.sporttrackerskeleton.ui.viewmodel.WorkoutViewModel
import app.skeleton.sporttrackerskeleton.ui.viewmodel.WorkoutsState
import org.koin.androidx.compose.koinViewModel

@Composable
fun WorkoutScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkoutViewModel = koinViewModel(),
    onNavigateToWorkoutDetails: (Int) -> Unit,
) {
    val workoutsState by viewModel.workoutsState.collectAsState()

    WorkoutScreenContent(
        modifier = modifier,
        workoutsState = workoutsState,
        onNavigateToWorkoutDetails = onNavigateToWorkoutDetails,
    )
}

@Composable
private fun WorkoutScreenContent(
    modifier: Modifier = Modifier,
    workoutsState: WorkoutsState,
    onNavigateToWorkoutDetails: (Int) -> Unit,
) {
    when (workoutsState) {
        is DataUiState.Data<List<WorkoutModel>> -> {
            WorkoutDataContent(
                workouts = workoutsState.data,
                modifier = modifier,
                onNavigateToWorkoutDetails = onNavigateToWorkoutDetails,
            )
        }

        DataUiState.Empty -> {
            NoDataContent(
                modifier = modifier,
                noContentMainText = {
                    Text(
                        text = stringResource(R.string.no_workouts_info),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
            )
        }

        // Some loading indicator
        DataUiState.Initial -> Unit
    }
}

@Composable
private fun WorkoutDataContent(
    workouts: List<WorkoutModel>,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    onNavigateToWorkoutDetails: (Int) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        WorkoutsList(
            workouts = workouts,
            modifier = Modifier.fillMaxSize(),
            onNavigateToWorkoutDetails = onNavigateToWorkoutDetails
        )
    }
}

@Composable
private fun WorkoutsList(
    workouts: List<WorkoutModel>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    overscrollEffect: OverscrollEffect? = rememberOverscrollEffect(),
    onNavigateToWorkoutDetails: (Int) -> Unit,
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
            items = workouts,
            key = { workout ->
                workout.id
            }
        ) { workout ->
            Unit
        }
    }
}