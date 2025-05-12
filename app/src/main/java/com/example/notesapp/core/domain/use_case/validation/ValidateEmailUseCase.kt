package com.example.notesapp.core.domain.use_case.validation

import com.example.notesapp.core.domain.util.error.EmailError
import com.example.notesapp.core.domain.util.Resource


class ValidateEmailUseCase {
    operator fun invoke(email: String): Resource<Unit, EmailError> {
        if (email.isBlank()) {
            return Resource.Error(EmailError.BLANK_FIELD)
        }
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        emailRegex.toRegex().matches(email)

        if (!emailRegex.toRegex().matches(email)
        ) {
            return Resource.Error(EmailError.INVALID_EMAIL)
        }
        return Resource.Success(Unit)
    }
}