package com.example.taskmanagerapp.ui.screen.taskform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskmanagerapp.ui.state.OperationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    viewModel: TaskFormViewModel,
    taskId: String?,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(taskId) {
        if (taskId != null) {
            viewModel.loadTaskDetails(taskId)
        }
    }

    LaunchedEffect(uiState.operationState) {
        if (uiState.operationState is OperationState.Success) {
            viewModel.resetOperationState()
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == null) "Nueva Tarea" else "Editar Tarea") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.onTitleChange(it) },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.description,
                onValueChange = { viewModel.onDescriptionChange(it) },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Estado de la tarea",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.Start)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = !uiState.completed,
                    onClick = { viewModel.onCompletedChange(false) }
                )
                Text(
                    text = "Pendiente",
                    modifier = Modifier.clickable { viewModel.onCompletedChange(false) }
                )
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = uiState.completed,
                    onClick = { viewModel.onCompletedChange(true) }
                )
                Text(
                    text = "Completada",
                    modifier = Modifier.clickable { viewModel.onCompletedChange(true) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.operationState is OperationState.Loading) {
                CircularProgressIndicator()
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { viewModel.saveTask(taskId) }) {
                        Text("Guardar")
                    }
                    if (taskId == null) {
                        OutlinedButton(onClick = { viewModel.saveAsDraft() }) {
                            Text("Guardar Borrador")
                        }
                    }
                }
            }

            val opState = uiState.operationState
            if (opState is OperationState.Error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = opState.message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}