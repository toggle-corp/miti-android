package com.togglecorp.miti2

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.togglecorp.miti2.converter.NepaliDate
import android.app.AlarmManager
import android.view.View
import com.togglecorp.miti2.mitidata.mitiData
import com.togglecorp.miti2.mitidata.readMitiData
import java.util.*


class MitiAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        setAlarm(context)
        readMitiData(context)

        val today = NepaliDate.today()
        val monthlyData = mitiData?.optJSONObject("data")?.optJSONObject(today.year.toString())?.optJSONArray(today.month.toString())
        val dateData = monthlyData?.optJSONObject(today.day - 1)
        val tithi = if (dateData?.isNull("tithi") == true) null else dateData?.optString("tithi")
        val extra = dateData?.optJSONObject("extra")
        val event = if (extra?.isNull("event") == true) null else extra?.optString("event")

        val todayEnglish = today.toEnglishDate()


        val dayHeaders = context.resources.getStringArray(R.array.days)
        val dayOfWeek = dayHeaders[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1]



        // Perform this loop procedure for each App Widget that belongs to this provider
        appWidgetIds.forEach { appWidgetId ->
            // Create an Intent to launch ExampleActivity
            val pendingIntent: PendingIntent = Intent(context, MainActivity::class.java)
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }

            val views = RemoteViews(
                context.packageName,
                R.layout.miti_appwidget
            )

            views.setOnClickPendingIntent(R.id.widgetContainer, pendingIntent)


            views.setTextViewText(R.id.nepaliDate, today.toString(context))
            views.setTextViewText(R.id.englishDate, todayEnglish.toString())
            views.setTextViewText(R.id.tithi, tithi)
            views.setTextViewText(R.id.event, event)
            views.setTextViewText(R.id.dayOfWeek, dayOfWeek)

            if (event.isNullOrBlank()) {
                views.setViewVisibility(R.id.event, View.GONE)
            }


            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    protected fun setAlarm(context: Context) {
        // Set alarm to wake up next day midnight
        val midnight = Calendar.getInstance()
        midnight.set(Calendar.HOUR_OF_DAY, 0)
        midnight.set(Calendar.MINUTE, 0)
        midnight.set(Calendar.SECOND, 1)
        midnight.set(Calendar.MILLISECOND, 0)
        midnight.add(Calendar.DAY_OF_YEAR, 1)

        val intent = Intent(context, javaClass)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.set(AlarmManager.RTC_WAKEUP, midnight.getTimeInMillis(), pendingIntent)
    }
}