package com.example.taskmanagerapp.domain.usecase.auth

import com.example.taskmanagerapp.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.logoutUser()
    }
}