package com.example.notesapp.feature.register.domain.use_case

import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.feature.register.domain.repository.RegisterRepository

class RegisterUseCase(
    private val registerRepository: RegisterRepository,
) {

    suspend operator fun invoke(email: String, password: String): Resource<Unit, NetworkError> {
        return registerRepository.register(email, password)
    }
}