package com.avis.skycast.data.repository

import com.avis.skycast.data.remote.api.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(

        lat: Double,
        lon: Double,
        apiKey: String

    ) = api.getWeather(
        lat,
        lon,
        apiKey
    )
}