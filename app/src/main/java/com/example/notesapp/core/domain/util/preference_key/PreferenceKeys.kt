package com.example.notesapp.core.domain.util.preference_key

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val ACCESS_TOKEN_KEY = stringPreferencesKey("user_access_token")
    val REFRESH_TOKEN_KEY = stringPreferencesKey("user_refresh_token")

}