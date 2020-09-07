package com.example.todo.data

import androidx.room.TypeConverter
import com.example.todo.data.model.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String{
        return priority.name
    }

    @TypeConverter
    fun toPriority(string: String): Priority {
        return Priority.valueOf(string)
    }
}