package com.avis.skycast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avis.skycast.data.local.User
import com.avis.skycast.data.repository.UserRepository
import com.avis.skycast.session.SessionManager
import com.avis.skycast.ui.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor( private val repository: UserRepository, private val sessionManager: SessionManager) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun registerUser(email: String, password: String) {

        viewModelScope.launch {

            _uiState.value = LoginUiState.Loading

            try {

                repository.registerUser(
                    User(
                        email = email, password = password
                    )
                )

                _uiState.value = LoginUiState.Success
                sessionManager.saveLoginState( true )
            } catch (e: Exception) {

                _uiState.value = LoginUiState.Error(
                    e.message ?: "Unknown Error"
                )
            }
        }
    }

    fun loginUser(email: String, password: String) {

        viewModelScope.launch {

            _uiState.value = LoginUiState.Loading

            try {

                val user = repository.loginUser(
                    email, password
                )

                if (user != null) {

                    _uiState.value = LoginUiState.Success
                    sessionManager.saveLoginState( true )
                } else {

                    _uiState.value = LoginUiState.Error(
                        "Invalid Credentials"
                    )
                }

            } catch (e: Exception) {

                _uiState.value = LoginUiState.Error(
                    e.message ?: "Unknown Error"
                )
            }
        }
    }
}