package app.skeleton.sporttrackerskeleton.ui.composable.screen.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.skeleton.sporttrackerskeleton.R
import app.skeleton.sporttrackerskeleton.ui.viewmodel.WelcomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignUp: () -> Unit,
    onNavigateToWorkout: () -> Unit,
    viewModel: WelcomeViewModel = koinViewModel()
) {
    val shouldNavigateToWorkout by viewModel.shouldNavigateToWorkout.collectAsState()

    LaunchedEffect(shouldNavigateToWorkout) {
        if (shouldNavigateToWorkout == true) {
            onNavigateToWorkout()
        }
    }

    if (shouldNavigateToWorkout == false) {
        WelcomeScreenContent(
            modifier = modifier,
            onNavigateToSignUp = onNavigateToSignUp,
        )
    }
}

@Composable
private fun WelcomeScreenContent(
    modifier: Modifier = Modifier,
    onNavigateToSignUp: () -> Unit
) {
    val pages = listOf(
        WelcomePageData(
            title = stringResource(R.string.welcome_page1_title),
            description = stringResource(R.string.welcome_page1_text)
        ),
        WelcomePageData(
            title = stringResource(R.string.welcome_page2_title),
            description = stringResource(R.string.welcome_page2_text)
        ),
        WelcomePageData(
            title = stringResource(R.string.welcome_page3_title),
            description = stringResource(R.string.welcome_page3_text)
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { pageIndex ->
                WelcomePage(data = pages[pageIndex])
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (pagerState.currentPage < pages.size - 1) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            onNavigateToSignUp()
                        }
                    }
                ) {
                    Text(
                        text = if (pagerState.currentPage < pages.size - 1) "Next" else "Get Started"
                    )
                }
            }
        }
    }
}

@Composable
private fun WelcomePage(data: WelcomePageData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = data.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = data.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

private data class WelcomePageData(
    val title: String,
    val description: String
)
