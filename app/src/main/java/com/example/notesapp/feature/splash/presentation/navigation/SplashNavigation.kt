package com.example.notesapp.feature.splash.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.notesapp.feature.login.presentation.navigation.Login
import com.example.notesapp.feature.note.presentation.navigation.Note
import com.example.notesapp.feature.splash.presentation.screen.SplashScreenRoot
import kotlinx.serialization.Serializable

@Serializable
object Splash

fun NavGraphBuilder.splashGraph(
    navController: NavController,
) {

    composable<Splash> {
        SplashScreenRoot(
            navigateToHomeScreen = {
                navController.navigate(Note) {
                    popUpTo<Splash> { inclusive = true }
                }
            },
            navigateToLoginScreen = {
                navController.navigate(
                    Login
                ) { popUpTo<Splash> { inclusive = true } }
            }
        )
    }
}