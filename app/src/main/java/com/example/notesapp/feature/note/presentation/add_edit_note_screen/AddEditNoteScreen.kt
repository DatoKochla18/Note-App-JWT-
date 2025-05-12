package com.example.notesapp.feature.note.presentation.add_edit_note_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.core.presentation.extension.CollectAsUiEvents
import com.example.notesapp.core.presentation.extension.asStringResource
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddEditNoteScreenRoot(
    viewModel: AddEditNoteViewModel = koinViewModel(),
    noteId: String? = null,
    navigateBack: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = noteId) {
        if (noteId != null) {
            viewModel.onEvent(AddEditNoteEvent.LoadNoteDetails(noteId))
        }
    }

    AddEditNoteScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        uiEvents = viewModel.uiEvents,
        navigateBack = navigateBack,
        snackBarHostState = snackBarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    uiState: AddEditNoteUiState,
    onEvent: (AddEditNoteEvent) -> Unit,
    uiEvents: Flow<AddEditNoteSideEffect>,
    navigateBack: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    val context = LocalContext.current
    val isEditing = uiState.id != null
    val colors = listOf(
        0xFF5C6BC0,  // Indigo
        0xFF26A69A,  // Teal
        0xFFEF5350,  // Red
        0xFFFFB300,  // Amber
        0xFF66BB6A,  // Green
        0xFF42A5F5,  // Blue
        0xFFAB47BC,  // Purple
        0xFFEC407A   // Pink
    )

    uiEvents.CollectAsUiEvents { event ->
        when (event) {
            is AddEditNoteSideEffect.ShowError -> {
                snackBarHostState.showSnackbar(
                    message = context.getString(event.message.asStringResource()),
                    duration = SnackbarDuration.Short
                )
            }

            AddEditNoteSideEffect.NavigateBack -> {
                navigateBack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (isEditing) "Edit Note" else "Add Note")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onEvent(AddEditNoteEvent.OnSaveNoteClicked) },
                        enabled = uiState.saveEnabled && !uiState.isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save note"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(colors) { color ->
                        ColorItem(
                            color = color,
                            isSelected = color == uiState.color,
                            onClick = { onEvent(AddEditNoteEvent.OnColorChanged(color)) }
                        )
                    }
                }

                // Note title
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = { onEvent(AddEditNoteEvent.OnTitleChanged(it)) },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.content,
                    onValueChange = { onEvent(AddEditNoteEvent.OnContentChanged(it)) },
                    label = { Text("Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

