package app.skeleton.sporttrackerskeleton.data.model

import androidx.annotation.StringRes

enum class WorkoutType(@field:StringRes val titleRes: Int) {
    // for example
    // STRENGTH(R.string.strength_workout)
    // CARDIO(R.string.cardio_workout)
    // HIIT(R.string.hiit_workout)
}

