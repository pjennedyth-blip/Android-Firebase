package com.example.taskmanagerapp.ui.screen.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerapp.domain.usecase.auth.RegisterUserUseCase
import com.example.taskmanagerapp.ui.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun register(email: String, password: String, passwordConfirm: String) {

        if (email.length == 0 ||
            password.length == 0 ||
            passwordConfirm.length == 0
        ) {
            _uiState.update {
                it.copy(
                    errorMessage = "Todos los campos son obligatorios"
                )
            }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update {
                it.copy(
                    errorMessage = "Ingresa un correo electrónico válido"
                )
            }
            return
        }

        if (password.length < 6) {
            _uiState.update {
                it.copy(
                    errorMessage = "La contraseña debe tener al menos 6 caracteres"
                )
            }
            return
        }

        if (password != passwordConfirm) {
            _uiState.update {
                it.copy(
                    errorMessage = "Las contraseñas no coinciden"
                )
            }
            return
        }

        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            val result = registerUserUseCase(email, password)

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "Error en el registro"
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}