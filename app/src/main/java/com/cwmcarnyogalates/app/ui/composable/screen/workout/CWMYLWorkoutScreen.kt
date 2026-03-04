package com.cwmcarnyogalates.app.ui.composable.screen.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cwmcarnyogalates.app.R
import com.cwmcarnyogalates.app.data.model.WorkoutType
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLWorkoutViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CWMYLWorkoutScreen(
    viewModel: CWMYLWorkoutViewModel = koinViewModel(),
) {
    val saved by viewModel.saved.collectAsStateWithLifecycle()
    var showDatePicker by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val savedMsg = stringResource(R.string.workout_saved)

    LaunchedEffect(saved) {
        if (saved) {
            snackbarHostState.showSnackbar(savedMsg)
            viewModel.resetSaved()
        }
    }

    if (showDatePicker) {
        val state = rememberDatePickerState(
            initialSelectedDateMillis = viewModel.selectedDate
                .atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let { millis ->
                        viewModel.setDate(
                            Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                        )
                    }
                    showDatePicker = false
                }) { Text(stringResource(R.string.btn_ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text(stringResource(R.string.btn_cancel)) }
            },
        ) {
            DatePicker(state = state)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(stringResource(R.string.screen_add_workout), style = MaterialTheme.typography.titleLarge)

        Text(stringResource(R.string.label_yoga_type), style = MaterialTheme.typography.labelLarge)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            WorkoutType.values().forEach { type ->
                FilterChip(
                    selected = viewModel.selectedType == type,
                    onClick = { viewModel.selectType(type) },
                    label = { Text(type.name.replace("_", " ")) },
                    shape = RoundedCornerShape(8.dp),
                )
            }
        }

        Column {
            Text(
                "${stringResource(R.string.label_duration)}: ${viewModel.durationMinutes} ${stringResource(R.string.label_minutes)}",
                style = MaterialTheme.typography.labelLarge,
            )
            Slider(
                value = viewModel.durationMinutes.toFloat(),
                onValueChange = { viewModel.setDuration(it.toInt()) },
                valueRange = 10f..180f,
                steps = 16,
                modifier = Modifier.fillMaxWidth(),
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("10", style = MaterialTheme.typography.labelLarge)
                Text("180", style = MaterialTheme.typography.labelLarge)
            }
        }

        TextButton(
            onClick = { showDatePicker = true },
            contentPadding = PaddingValues(0.dp),
        ) {
            Text("${stringResource(R.string.label_date)}: ${viewModel.selectedDate}")
        }

        OutlinedTextField(
            value = viewModel.notes,
            onValueChange = viewModel::setNotes,
            label = { Text(stringResource(R.string.label_notes)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4,
        )

        Button(
            onClick = viewModel::save,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(stringResource(R.string.btn_save))
        }

        SnackbarHost(hostState = snackbarHostState)
    }
}
