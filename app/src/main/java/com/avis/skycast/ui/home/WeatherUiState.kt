package com.avis.skycast.ui.home

import com.avis.skycast.data.remote.dto.WeatherResponse

sealed class WeatherUiState {

    object Idle : WeatherUiState()

    object Loading : WeatherUiState()

    data class Success(

        val data: WeatherResponse

    ) : WeatherUiState()

    data class Error(

        val message: String

    ) : WeatherUiState()
}