package com.example.taskmanagerapp.ui.screen.tasklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
    onNavigateToTaskForm: (String?) -> Unit,
    onNavigateToDrafts: () -> Unit,
    onLogoutSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoggedOut by viewModel.isLoggedOut.collectAsStateWithLifecycle()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDeleteId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut) {
            onLogoutSuccess()
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                taskToDeleteId = null
            },
            title = {
                Text("Eliminar tarea")
            },
            text = {
                Text("¿Estás seguro de que deseas eliminar esta tarea?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (taskToDeleteId != null) {
                            viewModel.deleteTask(taskToDeleteId!!)
                        }

                        showDeleteDialog = false
                        taskToDeleteId = null
                    }
                ) {
                    Text("Eliminar")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        taskToDeleteId = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Tareas") },
                actions = {
                    TextButton(onClick = onNavigateToDrafts) {
                        Text(
                            "Borradores",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    TextButton(
                        onClick = { viewModel.logout() }
                    ) {
                        Text(
                            "Salir",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateToTaskForm(null)
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Nueva Tarea"
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            if (uiState.isLoading) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            } else if (uiState.errorMessage != null) {

                Text(
                    text = "Error: " + uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error
                )

            } else if (uiState.tasks.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tienes tareas registradas.")
                }

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    items(uiState.tasks) { task ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onNavigateToTaskForm(task.id)
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Checkbox(
                                checked = task.completed,
                                onCheckedChange = {
                                    viewModel.toggleTaskCompletion(task)
                                }
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = task.title,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                 if (task.description.length > 0)   {
                                    Text(
                                        text = task.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            IconButton(
                                onClick = {
                                    taskToDeleteId = task.id
                                    showDeleteDialog = true
                                }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Eliminar"
                                )
                            }
                        }

                        HorizontalDivider()
                    }
                }
            }
        }
    }
}