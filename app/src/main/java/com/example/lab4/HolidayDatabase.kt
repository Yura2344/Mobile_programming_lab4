package com.example.lab4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Holiday::class], version = 1)
@TypeConverters(DateConverter::class, StringArrayConverter::class)
abstract class HolidayDatabase: RoomDatabase(){
    companion object{
        private var instance: HolidayDatabase? = null

        public fun getInstance(context: Context): HolidayDatabase?{
            if(instance == null){
                instance = Room.databaseBuilder(context.applicationContext, HolidayDatabase::class.java, "holidays_database").fallbackToDestructiveMigration().build()
            }
            return instance
        }
    }

    abstract fun holidayDao(): HolidayDao;
}