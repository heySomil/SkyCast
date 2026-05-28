package com.avis.skycast.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avis.skycast.ui.home.HomeScreen
import com.avis.skycast.ui.login.LoginScreen
import com.avis.skycast.viewmodel.MainViewModel

@Composable
fun Navigation() {

    val navController =
        rememberNavController()

    val viewModel: MainViewModel =
        hiltViewModel()

    val startDestination by
    viewModel.startDestination
        .collectAsState()

    NavHost(

        navController = navController,

        startDestination = startDestination
    ) {

        composable("login") {

            LoginScreen(

                onLoginSuccess = {

                    navController.navigate(
                        "home"
                    ) {

                        popUpTo("login") {

                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(onLogout = {
                navController.navigate("login") {
                    popUpTo(0)
                }
            })
        }
    }
}
