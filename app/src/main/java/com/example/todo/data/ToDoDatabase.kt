package com.example.todo.data

import android.content.Context
import androidx.room.*
import com.example.todo.data.model.ToDoData

@Database(entities = [ToDoData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    companion object {

        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                return Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_database"
                )
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}