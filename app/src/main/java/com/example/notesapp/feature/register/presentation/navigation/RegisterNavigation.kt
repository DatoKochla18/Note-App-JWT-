package com.example.notesapp.feature.register.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.notesapp.feature.register.presentation.screen.RegisterRootScreen
import kotlinx.serialization.Serializable

@Serializable
object Register


fun NavGraphBuilder.registerGraph(
    snackBarHostState: SnackbarHostState,
    navController: NavController,
) {

    composable<Register> {
        RegisterRootScreen(snackBarHostState = snackBarHostState) {
            navController.popBackStack()
        }
    }
}