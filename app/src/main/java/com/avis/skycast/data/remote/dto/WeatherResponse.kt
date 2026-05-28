package com.avis.skycast.data.remote.dto

data class WeatherResponse(

    val name: String,

    val main: Main
)

data class Main(

    val temp: Double
)