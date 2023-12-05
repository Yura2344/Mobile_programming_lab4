package com.example.lab4

import androidx.room.TypeConverter
import com.fasterxml.jackson.databind.ObjectMapper

class StringArrayConverter {
    @TypeConverter
    fun fromStringArray(value: Array<String>): String{
        return ObjectMapper().writeValueAsString(value)
    }

    @TypeConverter
    fun toStringArray(value: String): Array<String>{
        return ObjectMapper().readValue(value, Array<String>::class.java)
    }
}