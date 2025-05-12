package com.example.notesapp.feature.register.domain.util

import com.example.notesapp.core.domain.util.error.RootError

enum class RepeatPasswordError : RootError {
    NO_MATCH,
    BLANK_FIELD,
}