package com.cwmcarnyogalates.app.ui.composable.screen.welcome

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cwmcarnyogalates.app.R
import com.cwmcarnyogalates.app.data.model.BookingModel
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLBookingViewModel
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLWelcomeViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

private data class YogaArticle(val title: String, val description: String)

private val yogaArticles = listOf(
    YogaArticle(
        "Benefits of Hatha Yoga",
        "Hatha yoga is a classical yoga system combining asanas, pranayama, and meditation. Regular practice improves flexibility, strength, and overall well-being."
    ),
    YogaArticle(
        "Vinyasa: Yoga in Motion",
        "Vinyasa yoga links breath with fluid movement between poses. This dynamic style helps develop coordination, focus, and body awareness."
    ),
    YogaArticle(
        "Meditation and Mindfulness",
        "Even 10 minutes of daily meditation reduces stress levels, improves sleep quality, and boosts emotional intelligence."
    ),
    YogaArticle(
        "Yin Yoga for Recovery",
        "Yin yoga involves holding passive poses for extended periods. It deeply relaxes connective tissues and joints — ideal for recovery and restoration."
    ),
    YogaArticle(
        "Pranayama Breathing",
        "Pranayama breathing techniques are a powerful tool for managing energy and stress. Nadi shodhana practice balances the nervous system."
    ),
)

@Composable
fun CWMYLWelcomeScreen(
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: CWMYLWelcomeViewModel = koinViewModel(),
    bookingViewModel: CWMYLBookingViewModel = koinViewModel(),
) {
    val navigateToSignUp by viewModel.navigateToSignUp.collectAsStateWithLifecycle()
    val upcomingBookings by viewModel.upcomingBookings.collectAsStateWithLifecycle()
    val weeklyWorkouts by viewModel.weeklyWorkouts.collectAsStateWithLifecycle()
    val booked by bookingViewModel.booked.collectAsStateWithLifecycle()

    var showBookingDialog by remember { mutableStateOf(false) }

    LaunchedEffect(navigateToSignUp) {
        when (navigateToSignUp) {
            true -> onNavigateToSignUp()
            false -> { /* already home */ }
            null -> { /* loading */ }
        }
    }

    LaunchedEffect(booked) {
        if (booked) {
            showBookingDialog = false
            bookingViewModel.resetBooked()
        }
    }

    if (showBookingDialog) {
        BookingDialog(
            viewModel = bookingViewModel,
            onDismiss = { showBookingDialog = false },
        )
    }

    val weekCount = viewModel.getThisWeekCount(weeklyWorkouts)
    val pagerState = rememberPagerState { yogaArticles.size }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Column {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = stringResource(R.string.welcome_tagline),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.stats_this_week),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Text(
                        text = "$weekCount ${stringResource(R.string.stats_sessions)}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }

        item {
            Text(
                text = stringResource(R.string.section_articles),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(Modifier.height(8.dp))
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(end = 32.dp),
                pageSpacing = 12.dp,
            ) { page ->
                val article = yogaArticles[page]
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = article.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = article.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier.wrapContentHeight().fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                repeat(pagerState.pageCount) { i ->
                    val color = if (pagerState.currentPage == i)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    Box(
                        Modifier.padding(2.dp).size(8.dp)
                            .clip(CircleShape).background(color)
                    )
                }
            }
        }

        item {
            Button(
                onClick = { showBookingDialog = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(stringResource(R.string.btn_book_session))
            }
        }

        item {
            Text(
                text = stringResource(R.string.section_upcoming_sessions),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        if (upcomingBookings.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.no_upcoming_sessions),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                )
            }
        } else {
            items(upcomingBookings) { booking ->
                BookingCard(booking)
            }
        }
    }
}

@Composable
private fun BookingCard(booking: BookingModel) {
    val fmt = DateTimeFormatter.ofPattern("dd MMM yyyy")
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = booking.yogaType.name.replace("_", " "),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "${booking.date.format(fmt)} · ${booking.time}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                if (booking.instructorName.isNotBlank()) {
                    Text(
                        text = booking.instructorName,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        }
    }
}
