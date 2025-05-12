package com.example.notesapp.core.domain.use_case.validation

import com.example.notesapp.core.domain.util.error.PasswordError
import com.example.notesapp.core.domain.util.Resource

class ValidatePasswordUseCase {
    operator fun invoke(password: String): Resource<Unit, PasswordError> {
        if (password.length < 8) {
            return Resource.Error(PasswordError.SHORT_PASSWORD)
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return Resource.Error(PasswordError.INVALID_PASSWORD)
        }
        return Resource.Success(Unit)
    }


}