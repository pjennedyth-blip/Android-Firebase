package com.example.taskmanagerapp.ui.state

import com.example.taskmanagerapp.domain.model.TaskDraft
import java.util.ArrayList

data class DraftUiState(
    val isLoading: Boolean = false,
    val drafts: List<TaskDraft> = ArrayList<TaskDraft>(),
    val errorMessage: String? = null
)