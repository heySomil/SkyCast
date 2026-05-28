package com.avis.skycast.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.avis.skycast.service.WeatherService
import com.avis.skycast.viewmodel.HomeViewModel
import com.avis.skycast.viewmodel.MainViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun HomeScreen(onLogout: () -> Unit) {

    val viewModel: HomeViewModel = hiltViewModel()

    val weatherState by viewModel.weatherState.collectAsState()

    val context = LocalContext.current

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(
        context
    )
    val mainViewModel: MainViewModel = hiltViewModel()
    val permissionLauncher = rememberLauncherForActivityResult(

        contract = ActivityResultContracts.RequestPermission()

    ) { isGranted ->

        if (isGranted) {

            fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY, null
            ).addOnSuccessListener { location ->
                location?.let {
                    viewModel.fetchWeather(it.latitude, it.longitude)
                }
            }

        } else {

            Toast.makeText(
                context, "Permission Denied", Toast.LENGTH_SHORT
            ).show()
        }
    }
//LaunchEffect
    LaunchedEffect(Unit) {

        val permission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY, null
            ).addOnSuccessListener { location ->
                location?.let {
                    viewModel.fetchWeather(it.latitude, it.longitude)
                }
            }

        } else {

            permissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (val state = weatherState) {
            is WeatherUiState.Loading -> {
                CircularProgressIndicator()
            }

            is WeatherUiState.Success -> {

                LaunchedEffect(Unit) {

                    val serviceIntent = Intent(
                        context, WeatherService::class.java
                    )

                    serviceIntent.putExtra(
                        "city", state.data.name
                    )

                    serviceIntent.putExtra(
                        "temp", state.data.main.temp.toString()
                    )
                    ContextCompat.startForegroundService( context, serviceIntent )
                }

                Text(
                    text = "City: ${state.data.name}"
                )

                Text(
                    text = "Temperature: ${state.data.main.temp}°C"
                )
                Button(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            mainViewModel.logout()
                            onLogout()
                        }
                    }
                ) { Text( text = "Logout" ) }
            }


            is WeatherUiState.Error -> {
                Text(text = state.message)
            }

            else -> {
                Text(text = "Fetching Weather...")
            }
        }
    }
}