package com.example.notesapp.feature.profile.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.notesapp.feature.login.presentation.navigation.Login
import com.example.notesapp.feature.note.presentation.navigation.Note
import com.example.notesapp.feature.profile.presentation.screen.ProfileScreen
import kotlinx.serialization.Serializable


@Serializable
object Profile


fun NavGraphBuilder.profileGraph(
    snackBarHostState: SnackbarHostState,
    navController: NavController,
) {

    composable<Profile> {
        ProfileScreen(onNavigateToLogin = {
            navController.navigate(Login) {
                popUpTo<Note> { inclusive = true }
            }
        }, snackBarHostState = snackBarHostState)
    }
}