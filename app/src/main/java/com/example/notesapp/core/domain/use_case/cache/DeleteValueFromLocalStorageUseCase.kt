package com.example.notesapp.core.domain.use_case.cache

import androidx.datastore.preferences.core.Preferences
import com.example.notesapp.core.domain.manager.CacheManager

class DeleteValueFromLocalStorageUseCase(private val cacheManager: CacheManager) {

    suspend operator fun <T>invoke(key: Preferences.Key<T>) {
        return cacheManager.removeValue(key)
    }
}