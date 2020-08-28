package com.togglecorp.miti2.utils

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.app.NotificationManager
import android.app.PendingIntent
import com.togglecorp.miti2.MainActivity
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.preference.PreferenceManager
import com.togglecorp.miti2.R
import com.togglecorp.miti2.SettingsActivity
import com.togglecorp.miti2.converter.NepaliDate
import com.togglecorp.miti2.mitidata.mitiData




object DateNotification {
    val CHANNEL_ID = "com.togglecorp.miti.MITI"
    val CHANNEL_NAME = "MITI"

    fun show(context: Context) {
        val today = NepaliDate.today()

        // Title
        val title = today.toString(context)

        // Body
        val monthlyData = mitiData?.optJSONObject("data")?.optJSONObject(today.year.toString())?.optJSONArray(today.month.toString())
        val dateData = monthlyData?.optJSONObject(today.day - 1)

        val tithi = if (dateData?.isNull("tithi") == true) null else dateData?.optString("tithi")
        val extra = dateData?.optJSONObject("extra")
        val event = if (extra?.isNull("event") == true) null else extra?.optString("event")
        val holiday = if (extra?.isNull("holiday") == true) false else extra?.optBoolean("holiday") == true

        var body = ""
        if (tithi != null) {
            body += tithi

            if (event != null && event.length > 0) {
                body += ", " + event
            }

            if (holiday) {
                body += " (बिदा)"
            }
        } else {
            if (holiday) {
                body += "(बिदा)"
            }
        }


        // Notification manager
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        // Show in lock screen?
        val shownInLockScreen = PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(SettingsActivity.PREF_SHOW_DATE_LOCK_SCREEN, true)
        val visibility = if (shownInLockScreen) NotificationCompat.VISIBILITY_PUBLIC else NotificationCompat.VISIBILITY_SECRET

        // Notification channel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.lockscreenVisibility = visibility
            channel.setSound(null, null)
            notificationManager.createNotificationChannel(channel)
        }

        // Custom layout
        val remoteViews = RemoteViews(context.packageName, R.layout.notification)
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.subtitle, body)
        remoteViews.setImageViewResource(R.id.icon, DATE_ICONS[today.day - 1])

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(DATE_ICONS[today.day - 1])
            .setContent(remoteViews)
            .setVisibility(visibility)
            .setSound(null)
            .setShowWhen(false)
            .setOngoing(true)
        val resultIntent = Intent(context, MainActivity::class.java)

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)

        val resultPendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(resultPendingIntent)

        // Notify
        notificationManager.notify(100, builder.build())
    }

    fun clear(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(100)
    }

    fun refresh(context: Context) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (preferences.getBoolean(SettingsActivity.PREF_SHOW_DATE_IN_NOTIFICATION, false)) {
            show(context)
        } else {
            clear(context)
        }
    }
}