package com.example.taskmanagerapp.domain.usecase.auth

import com.example.taskmanagerapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.loginUser(email, password)
    }
}