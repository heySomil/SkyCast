package com.avis.skycast.di

import com.avis.skycast.data.remote.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(
                "https://api.openweathermap.org/data/2.5/"
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(
        retrofit: Retrofit
    ): WeatherApi {

        return retrofit.create(
            WeatherApi::class.java
        )
    }
}