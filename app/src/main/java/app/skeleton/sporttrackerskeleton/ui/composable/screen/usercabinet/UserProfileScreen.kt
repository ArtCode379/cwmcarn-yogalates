package app.skeleton.sporttrackerskeleton.ui.composable.screen.usercabinet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import app.skeleton.sporttrackerskeleton.R
import app.skeleton.sporttrackerskeleton.ui.composable.shared.UserTextField
import app.skeleton.sporttrackerskeleton.ui.composable.shared.UserTextFieldLabel
import app.skeleton.sporttrackerskeleton.ui.viewmodel.UserProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = koinViewModel(),
) {
    val isUserNameError by viewModel.userNameErrorState.collectAsState()

    UserProfileScreenContent(
        modifier = modifier,
        userNameFieldValue = viewModel.userName,
        userHeightFieldValue = viewModel.userHeight,
        userWeightFieldValue = viewModel.userWeight,
        userAgeFieldValue = viewModel.userAge,
        userGoalWorkoutsValue = viewModel.userGoalWorkouts,
        onUserNameFieldChange = viewModel::updateUserName,
        onUserHeightFieldChange = viewModel::updateUserHeight,
        onUserWeightFieldChange = viewModel::updateUserWeight,
        onUserAgeFieldChange = viewModel::updateUserAge,
        onUserGoalWorkoutsFieldChange = viewModel::updateUserGoalWorkouts,
        isUserNameError = isUserNameError,
        onUpdateUser = viewModel::updateUser,
    )
}

@Composable
private fun UserProfileScreenContent(
    modifier: Modifier = Modifier,
    userNameFieldValue: String,
    userHeightFieldValue: String,
    userWeightFieldValue: String,
    userAgeFieldValue: String,
    userGoalWorkoutsValue: String,
    onUserNameFieldChange: (String) -> Unit,
    onUserHeightFieldChange: (String) -> Unit,
    onUserWeightFieldChange: (String) -> Unit,
    onUserAgeFieldChange: (String) -> Unit,
    onUserGoalWorkoutsFieldChange: (String) -> Unit,
    isUserNameError: Boolean,
    onUpdateUser: () -> Unit,
) {
    Column(modifier = modifier) {
        UserProfileFields(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            userNameFieldValue = userNameFieldValue,
            userHeightFieldValue = userHeightFieldValue,
            userWeightFieldValue = userWeightFieldValue,
            userAgeFieldValue = userAgeFieldValue,
            userGoalWorkoutsValue = userGoalWorkoutsValue,
            onUserNameFieldChange = onUserNameFieldChange,
            onUserHeightFieldChange = onUserHeightFieldChange,
            onUserWeightFieldChange = onUserWeightFieldChange,
            onUserAgeFieldChange = onUserAgeFieldChange,
            onUserGoalWorkoutsFieldChange = onUserGoalWorkoutsFieldChange,
            isUserNameError = isUserNameError,
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        )

        UserProfileButtons(
            onSaveChangesClick = onUpdateUser,
            onRestoreProgressClick = { }
        )
    }
}

@Composable
private fun UserProfileFields(
    modifier: Modifier = Modifier,
    userNameFieldValue: String,
    userHeightFieldValue: String,
    userWeightFieldValue: String,
    userAgeFieldValue: String,
    userGoalWorkoutsValue: String,
    onUserNameFieldChange: (String) -> Unit,
    onUserHeightFieldChange: (String) -> Unit,
    onUserWeightFieldChange: (String) -> Unit,
    onUserAgeFieldChange: (String) -> Unit,
    onUserGoalWorkoutsFieldChange: (String) -> Unit,
    isUserNameError: Boolean,
    verticalArrangement: Arrangement.Vertical,
    horizontalAlignment: Alignment.Horizontal,
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        val focusManager = LocalFocusManager.current

        UserTextField(
            input = userNameFieldValue,
            onInputChange = onUserNameFieldChange,
            label = {
                UserTextFieldLabel(text = stringResource(R.string.user_text_field_name))
            },
            isError = isUserNameError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        UserTextField(
            input = userHeightFieldValue,
            onInputChange = onUserHeightFieldChange,
            label = {
                UserTextFieldLabel(text = stringResource(R.string.user_text_field_height))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        UserTextField(
            input = userWeightFieldValue,
            onInputChange = onUserWeightFieldChange,
            label = {
                UserTextFieldLabel(text = stringResource(R.string.user_text_field_weight))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        UserTextField(
            input = userAgeFieldValue,
            onInputChange = onUserAgeFieldChange,
            label = {
                UserTextFieldLabel(text = stringResource(R.string.user_text_field_age))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        UserTextField(
            input = userGoalWorkoutsValue,
            onInputChange = onUserGoalWorkoutsFieldChange,
            label = {
                UserTextFieldLabel(text = stringResource(R.string.user_text_field_goal_workout))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
    }
}

@Composable
private fun UserProfileButtons(
    onSaveChangesClick: () -> Unit,
    onRestoreProgressClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onSaveChangesClick,
        ) {
            Text(
                text = stringResource(R.string.button_update_text)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onRestoreProgressClick,
        ) {
            Text(
                text = stringResource(R.string.button_reset_progress_text)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}