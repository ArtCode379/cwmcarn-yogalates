package app.skeleton.sporttrackerskeleton.ui.composable.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import app.skeleton.sporttrackerskeleton.R
import app.skeleton.sporttrackerskeleton.ui.composable.shared.UserTextField
import app.skeleton.sporttrackerskeleton.ui.composable.shared.UserTextFieldLabel
import app.skeleton.sporttrackerskeleton.ui.state.DataUiState
import app.skeleton.sporttrackerskeleton.ui.viewmodel.SignUpViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = koinViewModel(),
    onNavigateToWorkoutScreen: () -> Unit
) {
    val isUserNameError by viewModel.userNameErrorState.collectAsState()
    val isUserSignedUp by viewModel.userSignedUpState.collectAsState()

    LaunchedEffect(isUserSignedUp) {
        if (isUserSignedUp is DataUiState.Data<Unit>) {
            onNavigateToWorkoutScreen()
        }
    }

    SignUpScreenContent(
        modifier = modifier,
        userNameFieldValue = viewModel.userName,
        userHeightFieldValue = viewModel.userHeight,
        userWeightFieldValue = viewModel.userWeight,
        onUserNameFieldChange = viewModel::updateUserName,
        onUserHeightFieldChange = viewModel::updateUserHeight,
        onUserWeightFieldChange = viewModel::updateUserWeight,
        isUserNameError = isUserNameError,
        onCompleteSignUp = viewModel::signUpUser,
    )
}

@Composable
private fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    userNameFieldValue: String,
    userHeightFieldValue: String,
    userWeightFieldValue: String,
    onUserNameFieldChange: (String) -> Unit,
    onUserHeightFieldChange: (String) -> Unit,
    onUserWeightFieldChange: (String) -> Unit,
    isUserNameError: Boolean,
    onCompleteSignUp: () -> Unit
) {
    Column(modifier = modifier) {
        SignUpHeader()

        SignUpFields(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            userNameFieldValue = userNameFieldValue,
            userHeightFieldValue = userHeightFieldValue,
            userWeightFieldValue = userWeightFieldValue,
            onUserNameFieldChange = onUserNameFieldChange,
            onUserHeightFieldChange = onUserHeightFieldChange,
            onUserWeightFieldChange = onUserWeightFieldChange,
            isUserNameError = isUserNameError,
            verticalArrangement = Arrangement.spacedBy(
                space = 50.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        )

        SignUpButton(
            onClick = onCompleteSignUp
        )
    }
}

@Composable
private fun SignUpHeader() {
    Column(modifier = Modifier.padding(start = 24.dp)) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.sign_up_screen_title),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.sign_up_screen_subtitle),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun SignUpFields(
    modifier: Modifier = Modifier,
    userNameFieldValue: String,
    userHeightFieldValue: String,
    userWeightFieldValue: String,
    onUserNameFieldChange: (String) -> Unit,
    onUserHeightFieldChange: (String) -> Unit,
    onUserWeightFieldChange: (String) -> Unit,
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
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
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
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
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
    }
}

@Composable
private fun SignUpButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.padding(vertical = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.button_sign_up_text)
            )
        }
    }
}