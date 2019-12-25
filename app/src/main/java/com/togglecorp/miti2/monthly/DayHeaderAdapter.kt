package com.togglecorp.miti2.monthly


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.togglecorp.miti2.R
import kotlinx.android.synthetic.main.day_header.view.*

class DayHeaderAdapter(val context: Context, val dayHeaders: Array<String>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.day_header, parent, false)
        } else {
            view = convertView
        }

        view.day.text = dayHeaders[position]
        if (position == 6) {
            view.day.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int = 7

}