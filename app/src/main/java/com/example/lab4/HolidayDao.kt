package com.example.lab4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HolidayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(holiday: Holiday);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(holidays: List<Holiday>);

    @Update
    suspend fun update(holiday: Holiday);

    @Query("DELETE FROM holidays")
    suspend fun cleanHolidays();

    @Delete
    suspend fun delete(holiday: Holiday);

    @Query("SELECT * FROM holidays")
    fun getAllHolidays(): LiveData<List<Holiday>>;
}