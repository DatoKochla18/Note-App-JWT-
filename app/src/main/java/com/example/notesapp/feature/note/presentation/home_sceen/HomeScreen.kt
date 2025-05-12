package com.example.notesapp.feature.note.presentation.home_sceen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.core.presentation.extension.CollectAsUiEvents
import com.example.notesapp.core.presentation.extension.asStringResource
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToAddEditScreen: (String?) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    val uiState by viewModel
        .uiState
        .collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(HomeEvent.GetNotes)
    }
    HomeScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        uiEvents = viewModel.uiEvents,
        navigateToAddEditScreen = navigateToAddEditScreen,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    uiEvents: Flow<HomeSideEffect>,
    navigateToAddEditScreen: (String?) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    val context = LocalContext.current

    uiEvents.CollectAsUiEvents { event ->
        when (event) {
            is HomeSideEffect.ShowError -> {
                snackBarHostState.showSnackbar(
                    message = context.getString(event.message.asStringResource()),
                    duration = SnackbarDuration.Short
                )
            }

            is HomeSideEffect.NavigateToAddOrEditNoteScreen -> {
                navigateToAddEditScreen(event.id)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Your Notes",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.notes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No notes yet. Add a new note!",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.notes) { note ->
                        NoteItem(
                            note = note,
                            onClick = { onEvent(HomeEvent.OnNoteClicked(note.id)) },
                            onDelete = { onEvent(HomeEvent.DeleteNote(note.id)) }
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navigateToAddEditScreen(null) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Note"
            )
        }
    }
}