package com.cwmcarnyogalates.app.data.model

import androidx.annotation.StringRes
import com.cwmcarnyogalates.app.R

enum class WorkoutType(@field:StringRes val titleRes: Int) {
    HATHA(R.string.workout_type_hatha),
    VINYASA(R.string.workout_type_vinyasa),
    ASHTANGA(R.string.workout_type_ashtanga),
    KUNDALINI(R.string.workout_type_kundalini),
    IYENGAR(R.string.workout_type_iyengar),
    YIN_YOGA(R.string.workout_type_yin_yoga),
    RESTORATIVE(R.string.workout_type_restorative),
    POWER_YOGA(R.string.workout_type_power_yoga),
}
