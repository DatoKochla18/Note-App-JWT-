package com.example.notesapp.feature.register.domain.use_case

import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.feature.register.domain.util.RepeatPasswordError


class ValidateRepeatPasswordUseCase {
    operator fun invoke(
        password: String,
        repeatedPassword: String,
    ): Resource<Unit, RepeatPasswordError> {

        if (repeatedPassword.isEmpty()) {
            return Resource.Error(RepeatPasswordError.BLANK_FIELD)
        }

        if (password != repeatedPassword) {
            return Resource.Error(RepeatPasswordError.NO_MATCH)
        }
        return Resource.Success(Unit)
    }
}