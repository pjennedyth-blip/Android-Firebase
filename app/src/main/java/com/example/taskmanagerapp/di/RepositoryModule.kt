package com.example.taskmanagerapp.di

import com.example.taskmanagerapp.data.repository.AuthRepositoryImpl
import com.example.taskmanagerapp.data.repository.DraftRepositoryImpl
import com.example.taskmanagerapp.data.repository.TaskRepositoryImpl
import com.example.taskmanagerapp.domain.repository.AuthRepository
import com.example.taskmanagerapp.domain.repository.DraftRepository
import com.example.taskmanagerapp.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindDraftRepository(
        draftRepositoryImpl: DraftRepositoryImpl
    ): DraftRepository
}