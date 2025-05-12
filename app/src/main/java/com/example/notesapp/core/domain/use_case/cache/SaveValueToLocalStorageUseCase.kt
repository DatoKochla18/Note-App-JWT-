package com.example.notesapp.core.domain.use_case.cache

import androidx.datastore.preferences.core.Preferences
import com.example.notesapp.core.domain.manager.CacheManager

class SaveValueToLocalStorageUseCase (
    private val cacheManager: CacheManager
) {
    suspend operator fun <T> invoke(key: Preferences.Key<T>, value: T) {
        cacheManager.saveValue(key, value)
    }
}