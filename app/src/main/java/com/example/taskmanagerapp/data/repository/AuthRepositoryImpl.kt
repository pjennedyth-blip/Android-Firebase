package com.example.taskmanagerapp.data.repository

import com.example.taskmanagerapp.data.remote.datasource.AuthRemoteDataSource
import com.example.taskmanagerapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun registerUser(email: String, password: String): Result<Unit> {
        return try {
            authRemoteDataSource.registerUser(email, password)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginUser(email: String, password: String): Result<Unit> {
        return try {
            authRemoteDataSource.loginUser(email, password)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logoutUser(): Result<Unit> {
        return try {
            authRemoteDataSource.logoutUser()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUserUid(): String? {
        return authRemoteDataSource.getCurrentUserUid()
    }
}