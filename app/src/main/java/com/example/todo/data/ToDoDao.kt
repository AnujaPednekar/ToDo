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

    @Query("SELECT * FROM TODO_TABLE ORDER BY CASE WHEN priority like 'H%' THEN 1 WHEN  priority like 'M%' THEN 2  WHEN priority like 'L%' THEN 3 END ")
    fun getDataSortedByHighPriority(): LiveData<List<ToDoData>>

    @Query("SELECT * FROM TODO_TABLE ORDER BY CASE WHEN priority like 'L%' THEN 1 WHEN  priority like 'M%' THEN 2  WHEN priority like 'H%' THEN 3 END ")
    fun getDataSortedByLowPriority(): LiveData<List<ToDoData>>
}