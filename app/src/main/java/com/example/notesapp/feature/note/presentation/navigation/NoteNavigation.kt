package com.example.notesapp.feature.note.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.notesapp.feature.note.presentation.add_edit_note_screen.AddEditNoteScreenRoot
import com.example.notesapp.feature.note.presentation.home_sceen.HomeScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object Note

@Serializable
data class NoteAddOrEdit(val id: String? = null)

fun NavGraphBuilder.noteGraph(navController: NavController, snackbarHostState: SnackbarHostState) {

    composable<Note> {
        HomeScreenRoot(
            navigateToAddEditScreen = { noteId ->
                navController.navigate(NoteAddOrEdit(noteId))
            },
            snackBarHostState = snackbarHostState
        )
    }

    composable<NoteAddOrEdit> { backStackEntry ->
        val noteAddOrEdit: NoteAddOrEdit = backStackEntry.toRoute()
        val noteId = noteAddOrEdit.id
        AddEditNoteScreenRoot(
            noteId = noteId,
            snackBarHostState = snackbarHostState,
            navigateBack = {
                navController.popBackStack()
            }
        )
    }
}