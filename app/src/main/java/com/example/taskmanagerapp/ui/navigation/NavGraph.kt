package com.example.taskmanagerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskmanagerapp.ui.screen.drafts.DraftsScreen
import com.example.taskmanagerapp.ui.screen.drafts.DraftsViewModel
import com.example.taskmanagerapp.ui.screen.login.LoginScreen
import com.example.taskmanagerapp.ui.screen.login.LoginViewModel
import com.example.taskmanagerapp.ui.screen.register.RegisterScreen
import com.example.taskmanagerapp.ui.screen.register.RegisterViewModel
import com.example.taskmanagerapp.ui.screen.taskform.TaskFormScreen
import com.example.taskmanagerapp.ui.screen.taskform.TaskFormViewModel
import com.example.taskmanagerapp.ui.screen.tasklist.TaskListScreen
import com.example.taskmanagerapp.ui.screen.tasklist.TaskListViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onLoginSuccess = {
                    navController.navigate("task_list") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            val viewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate("task_list") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("task_list") {
            val viewModel: TaskListViewModel = hiltViewModel()
            TaskListScreen(
                viewModel = viewModel,
                onNavigateToTaskForm = { taskId ->
                    if (taskId != null) {
                        navController.navigate("task_form?taskId=" + taskId)
                    } else {
                        navController.navigate("task_form")
                    }
                },
                onNavigateToDrafts = {
                    navController.navigate("drafts")
                },
                onLogoutSuccess = {
                    navController.navigate("login") {
                        popUpTo("task_list") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "task_form?taskId={taskId}",
            arguments = kotlin.collections.listOf(
                navArgument("taskId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val viewModel: TaskFormViewModel = hiltViewModel()
            TaskFormScreen(
                viewModel = viewModel,
                taskId = taskId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("drafts") {
            val viewModel: DraftsViewModel = hiltViewModel()
            DraftsScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}