package com.example.taskmanagerapp.di

import android.content.Context
import androidx.room.Room
import com.example.taskmanagerapp.data.local.dao.TaskDraftDao
import com.example.taskmanagerapp.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Suppress("UNCHECKED_CAST")
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        val clazz = Class.forName("com.example.taskmanagerapp.data.local.database.AppDatabase") as Class<AppDatabase>
        return Room.databaseBuilder(
            context,
            clazz,
            "task_manager_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDraftDao(database: AppDatabase): TaskDraftDao {
        return database.taskDraftDao()
    }
}