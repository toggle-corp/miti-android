package com.togglecorp.miti2.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import com.togglecorp.miti2.SettingsActivity
import android.content.BroadcastReceiver
import android.content.Context
import androidx.preference.PreferenceManager
import java.util.*


class DailyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        DateNotification.refresh(context)
    }

    companion object {

        fun setupAlarm(context: Context) {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            if (preferences.getBoolean(SettingsActivity.PREF_SHOW_DATE_IN_NOTIFICATION, false)) {
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, 0)
                }

                val intent = Intent(context, DailyBroadcastReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
            } else {
                val intent = Intent(context, DailyBroadcastReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.cancel(pendingIntent)
            }

            DateNotification.refresh(context)
        }
    }
}
