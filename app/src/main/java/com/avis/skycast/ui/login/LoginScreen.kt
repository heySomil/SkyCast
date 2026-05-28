package com.avis.skycast.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.avis.skycast.ui.LoginUiState
import com.avis.skycast.viewmodel.LoginViewModel

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {

    val viewModel: LoginViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()

    var email by remember {

        mutableStateOf("")
    }

    var password by remember {

        mutableStateOf("")
    }

    val context = LocalContext.current

    LaunchedEffect(uiState) {

        when (uiState) {

            is LoginUiState.Success -> {

                Toast.makeText(
                    context, "Login Success", Toast.LENGTH_SHORT
                ).show()

                onLoginSuccess()
            }

            is LoginUiState.Error -> {

                val message =
                    (uiState as LoginUiState.Error).message

                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "SkyCast Login"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(

            value = email,

            onValueChange = {

                email = it
            },

            label = {

                Text("Email")
            })

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(

            value = password,

            onValueChange = {

                password = it
            },

            label = {

                Text("Password")
            })

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(

            onClick = {

                viewModel.loginUser(
                    email, password
                )
            }) {

            Text("Login")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(

            onClick = {

                viewModel.registerUser(
                    email, password
                )
            }) {

            Text("Register")
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (uiState is LoginUiState.Loading) {

            CircularProgressIndicator()
        }
    }
}