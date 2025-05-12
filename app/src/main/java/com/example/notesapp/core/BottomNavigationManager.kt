package com.example.notesapp.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.notesapp.feature.note.presentation.navigation.Note
import com.example.notesapp.feature.profile.presentation.navigation.Profile

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)


object BottomNavigationManager {

    val toShowBottomNavList = listOf(
        Note, Profile
    )

    val topLevelRoutes = listOf(
        TopLevelRoute("Home", Note, Icons.Default.Home),
        TopLevelRoute("Profile", Profile, Icons.Default.Person),

        )
}