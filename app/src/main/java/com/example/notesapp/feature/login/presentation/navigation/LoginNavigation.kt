package com.example.notesapp.feature.login.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.notesapp.feature.login.presentation.screen.LoginScreenRoot
import com.example.notesapp.feature.note.presentation.navigation.Note
import com.example.notesapp.feature.register.presentation.navigation.Register
import kotlinx.serialization.Serializable

@Serializable
data object Login


fun NavGraphBuilder.loginGraph(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
) {
    composable<Login> {

        LoginScreenRoot(
            navigateToHomeScreen = {
                navController.navigate(Note) {
                    popUpTo<Login> { inclusive = true }
                }
            },
            navigateToRegisterScreen = {
                navController.navigate(Register)

            },
            snackBarHostState = snackBarHostState
        )
    }
}