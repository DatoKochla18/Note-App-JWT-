package com.example.notesapp.core.data.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.notesapp.core.domain.manager.CacheManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CacheManagerImpl(
    private val datastore: DataStore<Preferences>,
) : CacheManager {
    override suspend fun <T> saveValue(key: Preferences.Key<T>, value: T) {
        datastore.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun <T> readValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return datastore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    override suspend fun <T> removeValue(key: Preferences.Key<T>) {
        datastore.edit { preferences ->
            preferences.remove(key)
        }
    }
}