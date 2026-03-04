package com.cwmcarnyogalates.app.ui.composable.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cwmcarnyogalates.app.R
import com.cwmcarnyogalates.app.ui.viewmodel.CWMYLSignUpViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CWMYLSignUpScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    viewModel: CWMYLSignUpViewModel = koinViewModel(),
) {
    val nameError by viewModel.nameError.collectAsStateWithLifecycle()
    val signedUp by viewModel.signedUp.collectAsStateWithLifecycle()

    LaunchedEffect(signedUp) {
        if (signedUp) onNavigateToHome()
    }

    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.signup_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(
            value = viewModel.userName,
            onValueChange = viewModel::updateUserName,
            label = { Text(stringResource(R.string.label_name)) },
            isError = nameError,
            supportingText = if (nameError) {{ Text(stringResource(R.string.error_name_required)) }} else null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = viewModel::signUp,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.btn_get_started))
        }
    }
}
