package app.skeleton.sporttrackerskeleton.ui.composable.shared

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun UserTextFieldLabel(text: String){
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall
    )
}