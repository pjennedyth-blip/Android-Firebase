package com.example.taskmanagerapp.domain.usecase.auth

import com.example.taskmanagerapp.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): String? {
        return repository.getCurrentUserUid()
    }
}