package com.example.taskmanagerapp.ui.screen.taskform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerapp.domain.model.Task
import com.example.taskmanagerapp.domain.model.TaskDraft
import com.example.taskmanagerapp.domain.usecase.auth.GetCurrentUserUseCase
import com.example.taskmanagerapp.domain.usecase.draft.SaveDraftUseCase
import com.example.taskmanagerapp.domain.usecase.task.CreateTaskUseCase
import com.example.taskmanagerapp.domain.usecase.task.GetTasksUseCase
import com.example.taskmanagerapp.domain.usecase.task.UpdateTaskUseCase
import com.example.taskmanagerapp.ui.state.OperationState
import com.example.taskmanagerapp.ui.state.TaskFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val saveDraftUseCase: SaveDraftUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskFormUiState())
    val uiState: StateFlow<TaskFormUiState> = _uiState.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _uiState.update {
            it.copy(title = newTitle)
        }
    }

    fun onDescriptionChange(newDescription: String) {
        _uiState.update {
            it.copy(description = newDescription)
        }
    }

    fun onCompletedChange(newCompleted: Boolean) {
        _uiState.update {
            it.copy(completed = newCompleted)
        }
    }

    fun loadTaskDetails(taskId: String) {
        val uid = getCurrentUserUseCase() ?: return

        viewModelScope.launch {
            val tasksList = getTasksUseCase(uid).firstOrNull()

            if (tasksList != null) {
                for (task in tasksList) {
                    if (task.id == taskId) {
                        _uiState.update {
                            it.copy(
                                title = task.title,
                                description = task.description,
                                completed = task.completed,
                                createdAt = task.createdAt
                            )
                        }
                        break
                    }
                }
            }
        }
    }

    fun saveTask(taskId: String?) {
        val currentTitle = _uiState.value.title
        val currentDescription = _uiState.value.description

        if (currentTitle.length == 0) {
            _uiState.update {
                it.copy(
                    operationState = OperationState.Error(
                        "El título es obligatorio"
                    )
                )
            }
            return
        }

        val uid = getCurrentUserUseCase()

        if (uid == null) {
            _uiState.update {
                it.copy(
                    operationState = OperationState.Error(
                        "Usuario no autenticado"
                    )
                )
            }
            return
        }

        _uiState.update {
            it.copy(operationState = OperationState.Loading)
        }

        viewModelScope.launch {

            val result = if (taskId == null) {

                val newTask = Task(
                    title = currentTitle,
                    description = currentDescription,
                    ownerId = uid,
                    completed = _uiState.value.completed,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )

                createTaskUseCase(newTask)

            } else {

                val updatedTask = Task(
                    id = taskId,
                    title = currentTitle,
                    description = currentDescription,
                    ownerId = uid,
                    completed = _uiState.value.completed,
                    createdAt = _uiState.value.createdAt,
                    updatedAt = System.currentTimeMillis()
                )

                updateTaskUseCase(updatedTask)
            }

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        operationState = OperationState.Success
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        operationState = OperationState.Error(
                            result.exceptionOrNull()?.message
                                ?: "Error al guardar la tarea"
                        )
                    )
                }
            }
        }
    }

    fun saveAsDraft() {
        val currentTitle = _uiState.value.title
        val currentDescription = _uiState.value.description

        if (currentTitle.length == 0) {
            _uiState.update {
                it.copy(
                    operationState = OperationState.Error(
                        "El título es obligatorio para el borrador"
                    )
                )
            }
            return
        }

        val uid = getCurrentUserUseCase()

        if (uid == null) {
            _uiState.update {
                it.copy(
                    operationState = OperationState.Error(
                        "Usuario no autenticado"
                    )
                )
            }
            return
        }

        _uiState.update {
            it.copy(operationState = OperationState.Loading)
        }

        viewModelScope.launch {

            val draft = TaskDraft(
                ownerId = uid,
                title = currentTitle,
                description = currentDescription,
                savedAt = System.currentTimeMillis()
            )

            val result = saveDraftUseCase(draft)

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        operationState = OperationState.Success
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        operationState = OperationState.Error(
                            result.exceptionOrNull()?.message
                                ?: "Error al guardar el borrador"
                        )
                    )
                }
            }
        }
    }

    fun resetOperationState() {
        _uiState.update {
            it.copy(operationState = OperationState.Idle)
        }
    }
}