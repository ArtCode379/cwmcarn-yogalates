package app.skeleton.sporttrackerskeleton.ui.composable.screen.statistics

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.skeleton.sporttrackerskeleton.ui.viewmodel.StatisticsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticsViewModel = koinViewModel(),
) {
    StatisticsScreenContent(modifier = modifier)
}

@Composable
private fun StatisticsScreenContent(
    modifier: Modifier = Modifier
) {

}