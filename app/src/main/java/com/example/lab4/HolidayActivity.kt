package com.example.lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Locale

class HolidayActivity : AppCompatActivity() {
    private lateinit var linearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holiday)

        linearLayout = findViewById(R.id.holidayDetailsLayout)

        if (intent != null) {
            val holiday: Holiday = intent.extras?.getSerializable("holiday") as Holiday
            findViewById<TextView>(R.id.dateText).text = getString(
                R.string.holiday_date, SimpleDateFormat("dd.MM.yyyy", Locale.US).format(
                    holiday.date
                )
            )
            findViewById<TextView>(R.id.localNameText).text =
                getString(R.string.holiday_local_name, holiday.localName)
            findViewById<TextView>(R.id.nameText).text =
                getString(R.string.holiday_name, holiday.name)
            findViewById<TextView>(R.id.countryCodeText).text =
                getString(R.string.holiday_country_code, holiday.countryCode)
            findViewById<TextView>(R.id.fixedText).text =
                getString(R.string.holiday_fixed, holiday.fixed.toString())
            findViewById<TextView>(R.id.globalText).text =
                getString(R.string.holiday_global, holiday.global.toString())
            findViewById<TextView>(R.id.countiesText).text =
                getString(
                    R.string.holiday_counties,
                    holiday.counties?.joinToString(separator = ", ") ?: holiday.counties.toString()
                )
            findViewById<TextView>(R.id.launchYearText).text =
                getString(R.string.holiday_launch_year, holiday.launchYear.toString())
            findViewById<TextView>(R.id.typesText).text =
                getString(
                    R.string.holiday_types,
                    holiday.types?.joinToString(separator = ", ") ?: holiday.counties.toString()
                )
        }
    }
}