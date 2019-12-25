package com.togglecorp.miti2.mitidata

import android.content.Context
import com.togglecorp.miti2.R
import org.json.JSONObject

var mitiData: JSONObject? = null

fun readMitiData(context: Context) {
    val file = context.resources.openRawResource(R.raw.dump)
    val buffer = file.readBytes()
    val str = String(buffer)
    file.close()

    mitiData = JSONObject(str)
}

