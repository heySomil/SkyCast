package com.avis.skycast.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.avis.skycast.MainActivity
import com.avis.skycast.R

class WeatherService : Service() {

    companion object {

        const val CHANNEL_ID =
            "weather_channel"
    }

    override fun onCreate() {

        super.onCreate()

        createNotificationChannel()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        Log.d( "WeatherService -->", "Service Started" )
        val city =
            intent?.getStringExtra("city")
                ?: "Unknown"

        val temp =
            intent?.getStringExtra("temp")
                ?: "--"

        val notification =
            createNotification(
                city,
                temp
            )
        Log.d( "WeatherService-->", "Starting Foreground Notification" )
        startForeground(
            1,
            notification
        )

        return START_STICKY
    }

    private fun createNotification(
        city: String,
        temp: String
    ): Notification {

        val intent =
            Intent(
                this,
                MainActivity::class.java
            )

        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

        return NotificationCompat.Builder(
            this,
            CHANNEL_ID
        )
            .setContentTitle(
                "SkyCast Weather"
            )
            .setContentText(
                "$city : $temp°C"
            )
            .setSmallIcon(
                android.R.drawable.ic_menu_compass
            )
            .setContentIntent(
                pendingIntent
            )
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    "Weather Service",
                    NotificationManager
                        .IMPORTANCE_LOW
                )

            val manager =
                getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(
                channel
            )
        }
    }

    override fun onBind(
        intent: Intent?
    ): IBinder? = null
}