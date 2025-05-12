package com.example.notesapp.core.domain.use_case.cache

import androidx.datastore.preferences.core.Preferences
import com.example.notesapp.core.domain.manager.CacheManager
import kotlinx.coroutines.flow.Flow

class GetValueFromLocalStorageUseCase(
    private val cacheManager: CacheManager
) {
    operator fun <T> invoke(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return cacheManager.readValue(key, defaultValue)
    }
}