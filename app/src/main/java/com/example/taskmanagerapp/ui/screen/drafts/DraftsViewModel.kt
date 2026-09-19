package com.example.taskmanagerapp.ui.screen.drafts

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.withTimeout
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerapp.domain.model.TaskDraft
import com.example.taskmanagerapp.domain.usecase.auth.GetCurrentUserUseCase
import com.example.taskmanagerapp.domain.usecase.draft.DeleteDraftUseCase
import com.example.taskmanagerapp.domain.usecase.draft.GetDraftsUseCase
import com.example.taskmanagerapp.domain.usecase.draft.PublishDraftUseCase
import com.example.taskmanagerapp.ui.state.DraftUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DraftsViewModel @Inject constructor(
    private val getDraftsUseCase: GetDraftsUseCase,
    private val deleteDraftUseCase: DeleteDraftUseCase,
    private val publishDraftUseCase: PublishDraftUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DraftUiState(isLoading = true))
    val uiState: StateFlow<DraftUiState> = _uiState.asStateFlow()

    init {
        loadDrafts()
    }

    fun loadDrafts() {
        val uid = getCurrentUserUseCase()
        if (uid == null) {
            _uiState.update { it.copy(isLoading = false, errorMessage = "Usuario no autenticado") }
            return
        }
        viewModelScope.launch {
            getDraftsUseCase(uid)
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
                .collect { draftsList ->
                    _uiState.update { it.copy(isLoading = false, drafts = draftsList, errorMessage = null) }
                }
        }
    }

    fun publishDraft(draft: TaskDraft) {
        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            try {
                val result = withTimeout(10_000) {
                    publishDraftUseCase(draft)
                }

                if (result.isSuccess) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exceptionOrNull()?.message
                                ?: "Error al publicar borrador"
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No se pudo publicar el borrador. Verifica tu conexión a Internet."
                    )
                }
            }
        }
    }

    fun deleteDraft(draftId: Int) {
        viewModelScope.launch {
            deleteDraftUseCase(draftId)
        }
    }
}