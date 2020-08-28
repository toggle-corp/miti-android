package com.togglecorp.miti2

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.togglecorp.miti2.utils.DateNotification

class SettingsActivity : BaseActivity() {

    companion object {
        val PREF_DARK_THEME = "pref_dark_theme"
        val PREF_SHOW_DATE_IN_NOTIFICATION = "pref_show_date_notification"
        val PREF_SHOW_DATE_LOCK_SCREEN = "pref_show_date_lock_screen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onResume() {
            super.onResume()
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            if (key == PREF_SHOW_DATE_IN_NOTIFICATION || key == PREF_SHOW_DATE_LOCK_SCREEN) {
                DateNotification.refresh(activity!!)
            } else if (key == PREF_DARK_THEME) {
                activity?.recreate()
            }
        }

    }
}