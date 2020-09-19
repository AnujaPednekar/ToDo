package com.example.todo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo.data.model.ToDoData

@Dao
interface ToDoDao {

    @Query("SELECT * FROM TODO_TABLE ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Delete
    suspend fun delete(toDoData: ToDoData)

    @Query("DELETE FROM TODO_TABLE")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table where title like :searchQuery")
    fun search(searchQuery: String): LiveData<List<ToDoData>>
}