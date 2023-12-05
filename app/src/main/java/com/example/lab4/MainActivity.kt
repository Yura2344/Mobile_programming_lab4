package com.example.lab4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.text.SimpleDateFormat
import java.time.Year
import java.util.Locale

class MainActivity : AppCompatActivity() {

    interface NagerData {
        @GET("/api/v3/publicHolidays/{year}/{countryCode}")
        fun getHolidays(
            @Path("year") year: Int,
            @Path("countryCode") countryCode: String
        ): Call<List<Holiday>>
    }

    private val getURL = "https://date.nager.at"
    private lateinit var retrofit: Retrofit
    private lateinit var holidaysList: ListView

    private var holidays: List<Holiday> = listOf()

    private val holidaysViewList: MutableList<Pair<String, String>> = mutableListOf()
    private val itemsList: MutableList<HashMap<String, String>> = mutableListOf()
    private lateinit var adapter: SimpleAdapter

    private val database by lazy {
        Room.databaseBuilder(applicationContext, HolidayDatabase::class.java, "holidays.db").build()
    }

    private val holidayViewModel by viewModels<HolidayViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HolidayViewModel(database.holidayDao()) as T
                }
            }
        }
    )

    private fun dataToTable() {
        holidaysViewList.clear()
        itemsList.clear()
        for (i in holidays.indices) {
            holidaysViewList.add(
                Pair(
                    SimpleDateFormat("dd.MM.yyyy", Locale.US).format(
                        holidays[i].date
                    ), holidays[i].name!!
                )
            )
        }


        val it: Iterator<Pair<String, String>> = holidaysViewList.iterator()
        while (it.hasNext()) {
            val itemMap: HashMap<String, String> = hashMapOf()
            val pair: Pair<String, String> = it.next()
            itemMap["text1"] = pair.first
            itemMap["text2"] = pair.second
            itemsList.add(itemMap)
        }

        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = Retrofit.Builder()
            .baseUrl(getURL)
            .client(OkHttpClient())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        holidaysList = findViewById(R.id.holidaysList)
        holidaysList.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@MainActivity, HolidayActivity::class.java)
            intent.putExtra("holiday", holidays[id.toInt()])
            startActivity(intent)
        }
        adapter = SimpleAdapter(
            this@MainActivity,
            itemsList,
            android.R.layout.simple_list_item_2,
            arrayOf("text1", "text2"),
            arrayOf(android.R.id.text1, android.R.id.text2).toIntArray()
        )

        holidaysList.adapter = adapter

        holidayViewModel.getAllHolidays().observe(this@MainActivity) {
            holidays = it
            dataToTable()
        }

        val nagerData: NagerData = retrofit.create(NagerData::class.java)

        nagerData.getHolidays(Year.now().value, "US").enqueue(object : Callback<List<Holiday>> {
            override fun onResponse(call: Call<List<Holiday>>, response: Response<List<Holiday>>) {
                holidays = response.body()!!
                for(i in holidays.indices){
                    holidays[i].id = i.toLong()
                }
                holidayViewModel.replaceHolidays(holidays)
            }

            override fun onFailure(call: Call<List<Holiday>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Failed to load data from server, using cached data instead",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}