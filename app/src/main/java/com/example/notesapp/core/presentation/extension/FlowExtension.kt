package com.example.notesapp.core.presentation.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> Flow<T>.CollectAsUiEvents(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    onEvent: suspend (T) -> Unit,
) {
    val lifecycleOwner: LifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            this@CollectAsUiEvents.collect { event ->
                onEvent(event)
            }
        }
    }
}