package com.togglecorp.miti2

import android.os.Bundle
import com.togglecorp.miti2.converter.NepaliDate
import com.togglecorp.miti2.mitidata.readMitiData
import com.togglecorp.miti2.monthly.MonthAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Menu
import android.view.MenuItem


class MainActivity : BaseActivity() {

    private lateinit var monthAdapter : MonthAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_name_nepali)
        setContentView(R.layout.activity_main)

        readMitiData(this)

        val today = NepaliDate.today()

        monthAdapter = MonthAdapter(supportFragmentManager)
        monthlyPager.adapter = monthAdapter
        monthlyPager.currentItem = (today.year - NepaliDate.START_NEPALI_YEAR) * 12 + today.month - 1
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.today_button) {
            val today = NepaliDate.today()
            monthlyPager.currentItem = (today.year - NepaliDate.START_NEPALI_YEAR) * 12 + today.month - 1
        }
        return super.onOptionsItemSelected(item)
    }
}
