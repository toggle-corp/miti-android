package com.togglecorp.miti2.monthly

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.togglecorp.miti2.converter.NepaliDate

class MonthAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int  = NepaliDate.NUM_YEARS * 12

    override fun getItem(i: Int): Fragment {
        val fragment = MonthFragment()
        fragment.arguments = Bundle().apply {
            putInt("YEAR", (i / 12) + NepaliDate.START_NEPALI_YEAR)
            putInt("MONTH", (i % 12) + 1)
        }
        return fragment
    }
}