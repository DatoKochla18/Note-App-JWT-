package com.example.notesapp.core.domain.manager

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface CacheManager {
    suspend fun <T> saveValue(key: Preferences.Key<T>, value: T)
    fun <T> readValue(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun <T> removeValue(key: Preferences.Key<T>)
}