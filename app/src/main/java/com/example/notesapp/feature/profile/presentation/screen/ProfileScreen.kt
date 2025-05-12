package com.example.notesapp.feature.profile.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.R
import com.example.notesapp.core.presentation.extension.CollectAsUiEvents
import com.example.notesapp.core.presentation.extension.asStringResource
import com.example.notesapp.core.presentation.resource.Dimens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()



    ProfileScreenContent(
        uiState = uiState,
        sideEffects = viewModel.uiEvents,
        onEvent = viewModel::onEvent,
        navigateToLoginScreen = onNavigateToLogin,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun ProfileScreenContent(
    uiState: ProfileUiState,
    sideEffects: Flow<ProfileSideEffect>,
    navigateToLoginScreen: () -> Unit,
    onEvent: (ProfileEvent) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    val context = LocalContext.current

    sideEffects.CollectAsUiEvents { error ->
        when (error) {
            ProfileSideEffect.NavigateToLoginScreen -> navigateToLoginScreen()
            is ProfileSideEffect.ShowError -> {
                snackBarHostState.showSnackbar(
                    message = context.getString(error.message.asStringResource()),
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(Dimens.SPACING))

            Button(
                onClick = { onEvent(ProfileEvent.LogOut) },
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(stringResource(R.string.log_out))
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreenContent(
            uiState = ProfileUiState(isLoading = false),
            sideEffects = flowOf(),
            navigateToLoginScreen = {},
            onEvent = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}