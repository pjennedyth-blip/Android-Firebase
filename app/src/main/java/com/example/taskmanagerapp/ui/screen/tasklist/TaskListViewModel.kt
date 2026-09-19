package com.example.taskmanagerapp.ui.screen.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerapp.domain.model.Task
import com.example.taskmanagerapp.domain.usecase.auth.GetCurrentUserUseCase
import com.example.taskmanagerapp.domain.usecase.auth.LogoutUserUseCase
import com.example.taskmanagerapp.domain.usecase.task.DeleteTaskUseCase
import com.example.taskmanagerapp.domain.usecase.task.GetTasksUseCase
import com.example.taskmanagerapp.domain.usecase.task.UpdateTaskUseCase
import com.example.taskmanagerapp.ui.state.TaskListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskListUiState(isLoading = true))
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut: StateFlow<Boolean> = _isLoggedOut.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        val uid = getCurrentUserUseCase()
        if (uid == null) {
            _uiState.update { it.copy(isLoading = false, errorMessage = "Usuario no autenticado") }
            return
        }
        viewModelScope.launch {
            getTasksUseCase(uid)
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
                .collect { tasksList ->
                    _uiState.update { it.copy(isLoading = false, tasks = tasksList, errorMessage = null) }
                }
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(completed = !task.completed, updatedAt = System.currentTimeMillis())
            updateTaskUseCase(updatedTask)
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            deleteTaskUseCase(taskId)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUserUseCase()
            _isLoggedOut.value = true
        }
    }
}