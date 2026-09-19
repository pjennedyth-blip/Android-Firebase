package com.example.taskmanagerapp.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerapp.domain.usecase.auth.LoginUserUseCase
import com.example.taskmanagerapp.domain.usecase.auth.GetCurrentUserUseCase
import com.example.taskmanagerapp.ui.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        checkSession()
    }

    fun checkSession() {
        val uid = getCurrentUserUseCase()
        if (uid != null) {
            _uiState.update { it.copy(isLoggedIn = true) }
        }
    }

    fun login(email: String, password: String) {
        if (email.length == 0 || password.length == 0) {
            _uiState.update { it.copy(errorMessage = "Campos obligatorios") }
            return
        }
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            val result = loginUserUseCase(email, password)
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
            } else {
                _uiState.update { it.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message ?: "Error al iniciar sesión") }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}