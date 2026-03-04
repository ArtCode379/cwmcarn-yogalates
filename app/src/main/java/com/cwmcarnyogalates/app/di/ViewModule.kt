package com.cwmcarnyogalates.app.di

import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLAppViewModel
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLBookingViewModel
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLHistoryViewModel
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLSettingsViewModel
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLSignUpViewModel
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLStatisticsViewModel
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLUserProfileViewModel
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLWelcomeViewModel
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLWorkoutViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { CWMYLAppViewModel(userRepository = get(), themeManager = get()) }
    viewModel { CWMYLWorkoutViewModel(completeWorkoutRepository = get()) }
    viewModel { CWMYLHistoryViewModel(completeWorkoutRepository = get()) }
    viewModel { CWMYLSignUpViewModel(userRepository = get()) }
    viewModel { CWMYLUserProfileViewModel(userRepository = get(), bookingRepository = get()) }
    viewModel { CWMYLStatisticsViewModel(completeWorkoutRepository = get()) }
    viewModel { CWMYLWelcomeViewModel(userRepository = get(), completeWorkoutRepository = get(), bookingRepository = get()) }
    viewModel { CWMYLBookingViewModel(bookingRepository = get()) }
    viewModel { CWMYLSettingsViewModel(themeManager = get()) }
}
