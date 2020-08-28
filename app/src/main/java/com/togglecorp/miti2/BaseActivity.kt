package com.togglecorp.miti2
import android.os.Build
import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import java.util.*


open class BaseActivity : AppCompatActivity() {
    private var currentDarkTheme: Boolean = false
    private lateinit var sharedPref: SharedPreferences

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateBaseContextLocale(base))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val language = "ne"  // SharedPref.getInstance().getSavedLanguage()
        val locale = Locale(language)
        Locale.setDefault(locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLocale(context, locale)
        } else updateResourcesLocaleLegacy(context, locale)

    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        val configuration = context.getResources().getConfiguration()
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context {
        val resources = context.getResources()
        val configuration = resources.getConfiguration()
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.getDisplayMetrics())
        return context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        currentDarkTheme = sharedPref.getBoolean(SettingsActivity.PREF_DARK_THEME, false)

        setAppTheme(currentDarkTheme)
    }

    override fun onResume() {
        super.onResume()
        val theme = sharedPref.getBoolean(SettingsActivity.PREF_DARK_THEME, false)
        if(currentDarkTheme != theme)
            recreate()
    }

    private fun setAppTheme(darkMode: Boolean) {
        if (darkMode) {
            setTheme(R.style.DarkAppTheme)
            return
        }
        setTheme(R.style.AppTheme)
    }
}