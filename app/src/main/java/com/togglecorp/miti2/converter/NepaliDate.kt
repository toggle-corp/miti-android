package com.togglecorp.miti2.converter

import android.content.Context
import com.togglecorp.miti2.R
import java.util.*


data class NepaliDate(val year: Int = 0, val month: Int = 0, val day: Int = 0,
                      var tithi: String? = "",
                      var event: String? = "",
                      var holiday: Boolean = false) {

    companion object {
        val NEPALI_DAYS = arrayOf(
            intArrayOf(30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(30, 32, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(30, 32, 31, 32, 31, 31, 29, 30, 29, 30, 29, 31),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31),
            intArrayOf(31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
            intArrayOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30),
            intArrayOf(31, 31, 32, 32, 31, 30, 30, 30, 29, 30, 30, 30),
            intArrayOf(30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30),
            intArrayOf(31, 32, 31, 32, 30, 31, 30, 30, 29, 30, 30, 30),
            intArrayOf(30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30),
            intArrayOf(31, 31, 32, 31, 31, 31, 30, 30, 29, 30, 30, 30),
            intArrayOf(30, 31, 32, 32, 30, 31, 30, 30, 29, 30, 30, 30),
            intArrayOf(30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30),
            intArrayOf(30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30)
        )

        val START_NEPALI_YEAR = 2000
        val START_ENGLISH_YEAR = 1943
        val START_ENGLISH_MONTH = 4
        val START_ENGLISH_DAY = 14

        val NUM_YEARS = NEPALI_DAYS.size

        fun getStartEnglishDate() : EnglishDate {
            val c = Calendar.getInstance()
            c.set(START_ENGLISH_YEAR, START_ENGLISH_MONTH - 1, START_ENGLISH_DAY)
            return EnglishDate(c)
        }

        fun getDays(year: Int, month: Int) = NEPALI_DAYS[year - START_NEPALI_YEAR][month - 1]

        fun today() : NepaliDate {
            val englishDate = EnglishDate(Calendar.getInstance())
            return englishDate.toNepaliDate()!!
        }
    }

    fun toEnglishDate() : EnglishDate? {
        var days = 0
        val eYear = year - START_NEPALI_YEAR

        for (i in 0..eYear) {
            for (j in 0..11) {
                if (i == eYear && j == month - 1) {
                    days += day - 1
                    val c = getStartEnglishDate().calendar
                    c.add(Calendar.DATE, days)
                    return EnglishDate(
                        year=c.get(Calendar.YEAR),
                        month=c.get(Calendar.MONTH) + 1,
                        day=c.get(Calendar.DAY_OF_MONTH)
                    )
                } else
                    days += NEPALI_DAYS[i][j]
            }
        }
        return null
    }

    fun toString(context: Context): String {
        val monthNames = context.resources.getStringArray(R.array.nepaliMonths)
        return numToString(context, day) + " " + monthNames[month - 1] + " " + numToString(context, year)
    }
}
