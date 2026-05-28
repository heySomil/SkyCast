package com.avis.skycast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avis.skycast.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(

    private val sessionManager: SessionManager

) : ViewModel() {

    private val _startDestination =
        MutableStateFlow("login")

    val startDestination: StateFlow<String> =
        _startDestination

    init {

        viewModelScope.launch {

            sessionManager.isLoggedIn
                .collect {

                    if (it) {

                        _startDestination.value =
                            "home"

                    } else {

                        _startDestination.value =
                            "login"
                    }
                }
        }
    }

    suspend fun logout() {

        sessionManager.saveLoginState(
            false
        )
    }
}