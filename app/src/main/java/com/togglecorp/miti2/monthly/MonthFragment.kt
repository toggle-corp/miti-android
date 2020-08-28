package com.togglecorp.miti2.monthly

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.togglecorp.miti2.R
import com.togglecorp.miti2.converter.NepaliDate
import com.togglecorp.miti2.converter.numToString
import com.togglecorp.miti2.mitidata.mitiData
import kotlinx.android.synthetic.main.date_details.*
import kotlinx.android.synthetic.main.month_fragment.view.*
import java.util.*
import android.util.TypedValue



class MonthFragment : Fragment() {

    private lateinit var viewModel: MonthViewModel
    private lateinit var dateAdapter: DateAdapter
    var year: Int = NepaliDate.START_NEPALI_YEAR
    var month: Int = 1
    private var nepaliDates = arrayListOf<NepaliDate?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.month_fragment, container, false)

        dateAdapter = DateAdapter(context!!, nepaliDates) { select(it) }
        view.datesGrid.adapter = dateAdapter

        val dayHeaders = context!!.resources.getStringArray(R.array.dayShortNames)
        view.dayHeadersGrid.adapter = DayHeaderAdapter(context!!, dayHeaders)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.apply {
            year = getInt("YEAR")
            month = getInt("MONTH")
        }

        nepaliDates.clear()
        val startDate = NepaliDate(year, month, 1)
        val startEngDate = startDate.toEnglishDate()!!

        val dayOfWeek = startEngDate.calendar.get(Calendar.DAY_OF_WEEK) - 1
        val numDays = NepaliDate.getDays(startDate.year, startDate.month)

        val monthlyData = mitiData?.optJSONObject("data")?.optJSONObject(year.toString())?.optJSONArray(month.toString())

        val today = NepaliDate.today()


        // Gather all nepali dates for this month
        for (i in 0..(7*6)) {
            if (i >= dayOfWeek && i - dayOfWeek < numDays) {
                val date = startDate.day + i - dayOfWeek

                val dateData = monthlyData?.optJSONObject(date - 1)
                val tithi = if (dateData?.isNull("tithi") == true) null else dateData?.optString("tithi")
                val extra = dateData?.optJSONObject("extra")
                val event = if (extra?.isNull("event") == true) null else extra?.optString("event")
                val holiday = if (extra?.isNull("holiday") == true) false else extra?.optBoolean("holiday") == true

                val nepaliDate = NepaliDate(
                    startDate.year,
                    startDate.month,
                    date,
                    tithi,
                    event,
                    holiday
                )
                nepaliDates.add(nepaliDate)

                if (today.year == year && today.month == month && today.day == date) {
                    select(nepaliDate)
                }
            }
            else {
                nepaliDates.add(null)
            }
        }
        // Set today
        dateAdapter.setToday(today)
        // Refresh adapter
        dateAdapter.notifyDataSetChanged()

        // Update nepali and english month titles
        val monthNames = context!!.resources.getStringArray(R.array.nepaliMonths)
        view.nepaliMonthTitle.text = monthNames[month - 1] + ", " + numToString(context!!, year)

        val endEngDate = NepaliDate(startDate.year, startDate.month, startDate.day + 25).toEnglishDate()!!
        val sc = startEngDate.calendar
        val ec = endEngDate.calendar
        val engMonth1 = sc.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
        val engMonth2 = ec.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)

        val yearLabel: String
        if (startEngDate.year == endEngDate.year) {
            yearLabel = startEngDate.year.toString()
        } else {
            yearLabel = startEngDate.year.toString() + " / " + endEngDate.year.toString()
        }

        view.englishMonthTitle.text = engMonth1 + " / " + engMonth2 + ", " + yearLabel
    }

    override fun onResume() {
        super.onResume()
        val today = NepaliDate.today()
        dateAdapter.setToday(today)
        dateAdapter.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MonthViewModel::class.java)
    }

    fun dpToPixels(dp: Int) = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        )

    fun select(nepaliDate: NepaliDate) {
        dateDetailsNepali.text = nepaliDate.toString(context!!)
        dateDetailsEnglish.text = nepaliDate.toEnglishDate()?.toString()
        dateDetailsTithi.text = nepaliDate.tithi
        dateDetailsEvent.text = nepaliDate.event
        val sheetBehavior = BottomSheetBehavior.from(dateDetailsBottomSheet)
        sheetBehavior.peekHeight = dpToPixels(120).toInt()

        dateAdapter.select(nepaliDate)
        dateAdapter.notifyDataSetChanged()
    }
}
