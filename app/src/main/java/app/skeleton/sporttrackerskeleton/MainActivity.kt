package app.skeleton.sporttrackerskeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.skeleton.sporttrackerskeleton.ui.composable.approot.AppRoot
import app.skeleton.sporttrackerskeleton.ui.theme.SportTrackerSkeletonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportTrackerSkeletonTheme {
                AppRoot()
            }
        }
    }
}