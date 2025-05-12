package com.example.notesapp.feature.splash.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.notesapp.R
import com.example.notesapp.core.presentation.extension.CollectAsUiEvents
import com.example.notesapp.core.presentation.resource.Colors
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreenRoot(
    viewModel: SplashViewModel = koinViewModel(), navigateToLoginScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
) {
    SplashScreen(
        viewModel.uiEvents,
        navigateToLoginScreen = navigateToLoginScreen,
        navigateToHomeScreen = navigateToHomeScreen
    )
}

@Composable
fun SplashScreen(
    uiEvents: Flow<SplashSideEffect>,
    navigateToLoginScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
) {

    uiEvents.CollectAsUiEvents { event ->
        when (event) {
            SplashSideEffect.NavigateToHomeScreen -> {
                navigateToHomeScreen()
            }

            SplashSideEffect.NavigateToLoginScreen -> {
                navigateToLoginScreen()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.WHITE)
    ) {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.WHITE),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.launching), color = Colors.BLACK)
        }

    }
}