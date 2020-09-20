package com.example.todo.data.repository

import com.example.todo.data.ToDoDao
import com.example.todo.data.model.ToDoData

class TodoRepository(private val toDoDao: ToDoDao) {

    fun getAllData() = toDoDao.getAllData()
    suspend fun insertData(toDoData: ToDoData) = toDoDao.insertData(toDoData)
    suspend fun updateData(toDoData: ToDoData) = toDoDao.updateData(toDoData)
    suspend fun delete(toDoData: ToDoData) = toDoDao.delete(toDoData)
    suspend fun deleteAll() = toDoDao.deleteAll()
    fun search(searchQuery: String) = toDoDao.search(searchQuery)
    fun getDataSortedByHighPriority() = toDoDao.getDataSortedByHighPriority()
    fun getDataSortedByLowPriority() = toDoDao.getDataSortedByLowPriority()

}