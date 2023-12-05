package com.example.lab4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

class HolidayRepository(private val holidayDao: HolidayDao) {

    suspend fun insertHoliday(holiday: Holiday){
        holidayDao.insert(holiday)
    }

    suspend fun insertHolidays(holidays: List<Holiday>){
        holidayDao.insert(holidays)
    }

    suspend fun updateHoliday(holiday: Holiday){
        holidayDao.update(holiday)
    }

    suspend fun deleteHoliday(holiday: Holiday){
        holidayDao.delete(holiday)
    }

    suspend fun cleanHolidays(){
        holidayDao.cleanHolidays()
    }

    fun getAllHolidays(): LiveData<List<Holiday>> {
        return holidayDao.getAllHolidays()
    }
}