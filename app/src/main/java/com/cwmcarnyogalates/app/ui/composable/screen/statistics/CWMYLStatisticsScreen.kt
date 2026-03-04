package com.cwmcarnyogalates.app.ui.composable.screen.statistics

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cwmcarnyogalates.app.R
import com.cwmcarnyogalates.app.data.model.WorkoutType
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLStatisticsViewModel
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLTimeFilter
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CWMYLStatisticsScreen(
    viewModel: CWMYLStatisticsViewModel = koinViewModel(),
) {
    val workouts by viewModel.filteredWorkouts.collectAsStateWithLifecycle()
    val timeFilter by viewModel.timeFilter.collectAsStateWithLifecycle()
    val typeFilter by viewModel.typeFilter.collectAsStateWithLifecycle()

    val totalMinutes = workouts.sumOf { it.durationMinutes }
    val streak = viewModel.longestStreak(workouts)
    val byType = viewModel.countByType(workouts)
    val maxCount = byType.values.maxOrNull()?.takeIf { it > 0 } ?: 1

    val barColors = listOf(
        Color(0xFF6B4C9A), Color(0xFF4CAF82), Color(0xFFFF8A65),
        Color(0xFF2196F3), Color(0xFFFF5722), Color(0xFF009688),
        Color(0xFF9C27B0), Color(0xFFFF9800),
    )

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Text(stringResource(R.string.screen_statistics), style = MaterialTheme.typography.titleLarge)
        }

        item {
            Text(stringResource(R.string.label_time_filter), style = MaterialTheme.typography.labelLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CWMYLTimeFilter.values().forEach { f ->
                    FilterChip(
                        selected = timeFilter == f,
                        onClick = { viewModel.timeFilter.value = f },
                        label = {
                            Text(
                                when (f) {
                                    CWMYLTimeFilter.ALL -> stringResource(R.string.filter_all)
                                    CWMYLTimeFilter.WEEK -> stringResource(R.string.filter_week)
                                    CWMYLTimeFilter.MONTH -> stringResource(R.string.filter_month)
                                }
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                    )
                }
            }
        }

        item {
            Text(stringResource(R.string.label_type_filter), style = MaterialTheme.typography.labelLarge)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = typeFilter == null,
                    onClick = { viewModel.typeFilter.value = null },
                    label = { Text(stringResource(R.string.filter_all)) },
                    shape = RoundedCornerShape(8.dp),
                )
                WorkoutType.values().forEach { type ->
                    FilterChip(
                        selected = typeFilter == type,
                        onClick = { viewModel.typeFilter.value = type },
                        label = { Text(type.name.replace("_", " ")) },
                        shape = RoundedCornerShape(8.dp),
                    )
                }
            }
        }

        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    label = stringResource(R.string.stats_total_workouts),
                    value = "${workouts.size}",
                    modifier = Modifier.weight(1f),
                )
                StatCard(
                    label = stringResource(R.string.stats_total_minutes),
                    value = "$totalMinutes",
                    modifier = Modifier.weight(1f),
                )
                StatCard(
                    label = stringResource(R.string.stats_streak),
                    value = "$streak",
                    modifier = Modifier.weight(1f),
                )
            }
        }

        item {
            Text(stringResource(R.string.stats_by_type), style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
            ) {
                Column(Modifier.padding(16.dp)) {
                    val types = WorkoutType.values()
                    Canvas(
                        modifier = Modifier.fillMaxWidth().height(160.dp),
                    ) {
                        val barWidth = (size.width - 16.dp.toPx()) / types.size
                        val maxH = size.height - 24.dp.toPx()
                        types.forEachIndexed { i, type ->
                            val count = byType[type] ?: 0
                            val barH = if (count > 0) (count.toFloat() / maxCount) * maxH else 0f
                            val color = barColors[i % barColors.size]
                            drawRect(
                                color = color,
                                topLeft = Offset(
                                    x = i * barWidth + 4.dp.toPx(),
                                    y = size.height - barH - 20.dp.toPx(),
                                ),
                                size = Size(barWidth - 8.dp.toPx(), barH),
                            )
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        WorkoutType.values().forEachIndexed { i, type ->
                            val count = byType[type] ?: 0
                            Text(
                                text = "${type.name.replace("_", " ")}: $count",
                                style = MaterialTheme.typography.labelLarge,
                                color = barColors[i % barColors.size],
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(value, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}
