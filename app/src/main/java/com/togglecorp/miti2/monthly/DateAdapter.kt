package com.togglecorp.miti2.monthly

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.togglecorp.miti2.R
import com.togglecorp.miti2.converter.NepaliDate
import com.togglecorp.miti2.converter.numToString
import com.togglecorp.miti2.utils.getThemeColor
import kotlinx.android.synthetic.main.date.view.*

class DateAdapter(val context: Context, val dates: ArrayList<NepaliDate?>, val onSelected: (NepaliDate) -> Unit) : BaseAdapter() {
    private var today : NepaliDate? = null
    private var selected: NepaliDate? = null

    fun setToday(date: NepaliDate) {
        today = date
    }

    fun select(date: NepaliDate) {
        selected = date
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.date, parent, false)
        } else {
            view = convertView
        }

        val nepaliDateView = view.nepaliDate
        val englishDateView = view.englishDate
        val tithiView = view.tithi
        val eventView = view.event

        val nepaliDate = dates[position]
        val englishDate = nepaliDate?.toEnglishDate()

        if (nepaliDate == null) {
            nepaliDateView.text = ""
            englishDateView.text = ""
            tithiView.text = ""
            eventView.text = ""
        } else {
            nepaliDateView.text = numToString(context, nepaliDate.day)
            englishDateView.text = englishDate!!.day.toString()
            tithiView.text = nepaliDate.tithi ?: ""
            eventView.text = nepaliDate.event ?: ""
        }

        if (nepaliDate?.holiday == true || position % 7 == 6) {
            nepaliDateView.setTextColor(
                getThemeColor(
                    context,
                    R.attr.colorAccent
                )
            )
            nepaliDateView.setTypeface(nepaliDateView.typeface, Typeface.NORMAL)
        } else {
            nepaliDateView.setTextColor(
                getThemeColor(
                    context,
                    android.R.attr.textColorPrimary
                )
            )
            nepaliDateView.setTypeface(nepaliDateView.typeface, Typeface.NORMAL)
        }


        if (nepaliDate?.day == today?.day && nepaliDate?.year == today?.year && nepaliDate?.month == today?.month) {
            view.setBackgroundColor(
                getThemeColor(
                    context,
                    R.attr.colorHighlight
                )
            )
        } else if (selected != null && (nepaliDate?.day == selected?.day && nepaliDate?.year == selected?.year && nepaliDate?.month == selected?.month)) {
            view.setBackgroundColor(
                getThemeColor(
                    context,
                    R.attr.colorSelection
                )
            )
        } else {
            view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }

        view.setOnClickListener { if (nepaliDate != null) onSelected(nepaliDate) }
        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int = dates.size

}