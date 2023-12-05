package com.example.lab4
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HolidayViewModel(holidayDao: HolidayDao) : ViewModel() {
    private var holidayRepository: HolidayRepository = HolidayRepository(holidayDao)
    private var allHolidays: LiveData<List<Holiday>> = holidayRepository.getAllHolidays()

    fun addHoliday(holiday: Holiday){
        viewModelScope.launch(Dispatchers.IO) {
            holidayRepository.insertHoliday(holiday)
        }
    }

    fun addHolidays(holidays: List<Holiday>){
        viewModelScope.launch(Dispatchers.IO) {
            holidayRepository.insertHolidays(holidays)
        }
    }

    fun replaceHolidays(holidays: List<Holiday>){
        viewModelScope.launch(Dispatchers.IO) {
            async { holidayRepository.cleanHolidays() }.await()
            async { holidayRepository.insertHolidays(holidays) }.await()
        }
    }

    fun updateHoliday(holiday: Holiday){
        viewModelScope.launch(Dispatchers.IO) {
            holidayRepository.updateHoliday(holiday)
        }
    }

    fun removeHoliday(holiday: Holiday){
        viewModelScope.launch(Dispatchers.IO) {
            holidayRepository.deleteHoliday(holiday)
        }
    }

    fun cleanHolidays(){
        viewModelScope.launch(Dispatchers.IO) {
            holidayRepository.cleanHolidays()
        }
    }

    fun getAllHolidays(): LiveData<List<Holiday>>{
        return allHolidays
    }
}