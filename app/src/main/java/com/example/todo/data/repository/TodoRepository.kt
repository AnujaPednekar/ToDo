package com.example.todo.data.repository

import com.example.todo.data.ToDoDao
import com.example.todo.data.model.ToDoData

class TodoRepository(private val toDoDao: ToDoDao) {

    fun getAllData() = toDoDao.getAllData()
    suspend fun insertData(toDoData: ToDoData) = toDoDao.insertData(toDoData)

}