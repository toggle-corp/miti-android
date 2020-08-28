package com.togglecorp.miti2.utils

import android.content.Context
import android.util.TypedValue
import com.togglecorp.miti2.R


fun getThemeColor(context: Context, attribute: Int): Int {
    val value = TypedValue()
    context.theme.resolveAttribute(attribute, value, true)
    return value.data
}

val DATE_ICONS = arrayOf(
    R.drawable.date_icon_1,
    R.drawable.date_icon_2,
    R.drawable.date_icon_3,
    R.drawable.date_icon_4,
    R.drawable.date_icon_5,
    R.drawable.date_icon_6,
    R.drawable.date_icon_7,
    R.drawable.date_icon_8,
    R.drawable.date_icon_9,
    R.drawable.date_icon_10,
    R.drawable.date_icon_11,
    R.drawable.date_icon_12,
    R.drawable.date_icon_13,
    R.drawable.date_icon_14,
    R.drawable.date_icon_15,
    R.drawable.date_icon_16,
    R.drawable.date_icon_17,
    R.drawable.date_icon_18,
    R.drawable.date_icon_19,
    R.drawable.date_icon_20,
    R.drawable.date_icon_21,
    R.drawable.date_icon_22,
    R.drawable.date_icon_23,
    R.drawable.date_icon_24,
    R.drawable.date_icon_25,
    R.drawable.date_icon_26,
    R.drawable.date_icon_27,
    R.drawable.date_icon_28,
    R.drawable.date_icon_29,
    R.drawable.date_icon_30,
    R.drawable.date_icon_31,
    R.drawable.date_icon_32
)