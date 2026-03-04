package app.skeleton.sporttrackerskeleton.ui.composable.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NoDataContent(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    noContentMainText: (@Composable () -> Unit),
    noContentSubtext: (@Composable () -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        icon?.invoke()
        noContentMainText.invoke()
        noContentSubtext?.invoke()
    }
}