package com.example.fitnesspro

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.format.*
import androidx.annotation.RequiresApi
import androidx.work.*
import com.example.fitnesspro.work.NotifyWeightWorker
import com.example.fitnesspro.work.WeightAlarmReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

class FitnessProApplication: Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
            setupWeightNotification()
        }
    }

    private fun setupWeightNotification() {
        // TODO: allow customization of when the notification fires
        // sets up a notification every day to log your weight, starting tomorrow
        val updateTime = Calendar.getInstance(Locale.getDefault())
        updateTime.set(Calendar.HOUR_OF_DAY, 0)
        updateTime.set(Calendar.MINUTE, 5)
        updateTime.add(Calendar.DAY_OF_YEAR, 1)

        Timber.d("Setting up new notification to fire at ${DateFormat.getDateTimeInstance().format(Date(updateTime.timeInMillis))}")
        val notifyIntent = Intent(applicationContext, WeightAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, notifyIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmManager?.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}