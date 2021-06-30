package com.example.fitnesspro.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.fitnesspro.MainActivity
import com.example.fitnesspro.R
import timber.log.Timber

class WeightAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) {
            return
        }
        sendNotification(context, 0)
    }

    private fun sendNotification(context: Context, id: Int) {
        Timber.d("Sending AddWeight notification...")
        // TODO: if I need to send more notifications, probably refactor this out into a separate
        //       class

        // create intent to open the AddWeightFragment on tap
        val pendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.add_weight_fragment)
            .createPendingIntent()

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
            .setContentTitle(context.getString(R.string.add_weight_notif_title))
            .setContentText(context.getString(R.string.add_weight_notif_desc))
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)        // start app on click
            .setAutoCancel(true)                    // remove notification on click

        // set channel and importance for Android 8.0 and above
        // see https://developer.android.com/training/notify-user/build-notification#add_the_support_library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "FitnessPro Weight Channel"
            val descriptionText = "Yo this is a cool channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // register the channel
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // NOTE: May also set ringtone, audio attributes, lights, light color, and vibration pattern

            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(context)) {
            // TODO: why use this ID vs other id
            notify(id, builder.build())
        }
    }

    companion object {
        // TODO: refactor some names into string resources
        const val NOTIFICATION_ID = "fitnesspro_notification_id"
        const val NOTIFICATION_NAME = "fitnesspro_weight"
        const val NOTIFICATION_CHANNEL = "fitnesspro_channel_01"
        const val NOTIFICATION_WORK = "fitnesspro_notification_work"
    }
}