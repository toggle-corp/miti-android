package com.togglecorp.miti2.converter

import java.util.*


data class EnglishDate(val year: Int = 0, val month: Int = 0, val day: Int = 0) {

    val calendar: Calendar
        get() {
            val c = Calendar.getInstance()
            c.timeInMillis = 0
            c.set(year, month - 1, day, 0, 0, 0)
            return c
        }

    val julianDate: Int
        get() {
            val a = (14 - month) / 12
            val y = year + 4800 - a
            val m = month + 12 * a - 3
            return day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045
        }

    fun toNepaliDate() : NepaliDate? {
        var days = julianDate - NepaliDate.getStartEnglishDate().julianDate + 1

        for (i in 0 until NepaliDate.NUM_YEARS) {
            for (j in 0..11) {
                if (days > NepaliDate.NEPALI_DAYS[i][j])
                    days -= NepaliDate.NEPALI_DAYS[i][j]
                else
                    return NepaliDate(i + NepaliDate.START_NEPALI_YEAR, j + 1, days)
            }
        }
        return null
    }

    constructor(calendar: Calendar) : this(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    override fun toString() : String {
        val c = calendar
        return day.toString() + " " + c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + " " + year.toString()
    }
}