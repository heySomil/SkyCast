package com.avis.skycast.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avis.skycast.data.repository.WeatherRepository
import com.avis.skycast.ui.home.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val repository: WeatherRepository

) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherUiState>(
        WeatherUiState.Idle
    )

    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    fun fetchWeather(

        lat: Double, lon: Double

    ) {

        viewModelScope.launch {

            _weatherState.value = WeatherUiState.Loading

            try {
                Log.d("TAG", "fetchWeather: -->$lat, $lon")
                val response = repository.getWeather(
                    lat, lon, "85d7c58222203c2577e99157be26f0c3"
                )

                _weatherState.value = WeatherUiState.Success(
                    response
                )

            } catch (e: Exception) {

                _weatherState.value = WeatherUiState.Error(
                    e.message ?: "Unknown Error"
                )
            }
        }
    }
}