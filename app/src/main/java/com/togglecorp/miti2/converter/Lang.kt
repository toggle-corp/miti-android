package com.togglecorp.miti2.converter

import android.content.Context

val digits = arrayOf('०', '१', '२', '३', '४', '५', '६', '७','८', '९')

fun numToString(context: Context, num: Int) : String {
    val str = num.toString()
//    if (context.resources.configuration.locale.language == "ne") {
        return str.map { digits[it - '0'] }.joinToString(separator = "")
//    }

//    return str
}