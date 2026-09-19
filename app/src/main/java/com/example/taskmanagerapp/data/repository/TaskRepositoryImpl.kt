package com.example.taskmanagerapp.data.repository

import com.example.taskmanagerapp.data.mapper.toDocument
import com.example.taskmanagerapp.data.mapper.toDomain
import com.example.taskmanagerapp.data.remote.datasource.TaskRemoteDataSource
import com.example.taskmanagerapp.domain.model.Task
import com.example.taskmanagerapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.ArrayList
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val remoteDataSource: TaskRemoteDataSource
) : TaskRepository {

    override fun getTasks(ownerId: String): Flow<List<Task>> {
        return remoteDataSource.observeTasks(ownerId).map { documentList ->
            val tasks = ArrayList<Task>()
            for (doc in documentList) {
                tasks.add(doc.toDomain())
            }
            tasks
        }
    }

    override suspend fun createTask(task: Task): Result<String> {
        return try {
            val id = remoteDataSource.createTask(task.toDocument())
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTask(task: Task): Result<Unit> {
        return try {
            remoteDataSource.updateTask(task.toDocument())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> {
        return try {
            remoteDataSource.deleteTask(taskId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}