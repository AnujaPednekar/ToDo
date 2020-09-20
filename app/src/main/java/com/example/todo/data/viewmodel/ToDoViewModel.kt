package com.example.todo.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.data.ToDoDatabase
import com.example.todo.data.model.ToDoData
import com.example.todo.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: TodoRepository

    init {
        repository = TodoRepository(toDoDao)
        getAllData()
    }

    fun getAllData() = repository.getAllData()

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    fun delete(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(toDoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun search(searchQuery: String): LiveData<List<ToDoData>> {
        return repository.search(searchQuery)
    }

    fun getDataSortedByHighPriority(): LiveData<List<ToDoData>> {
        return repository.getDataSortedByHighPriority()
    }

    fun getDataSortedByLowPriority(): LiveData<List<ToDoData>> {
        return repository.getDataSortedByLowPriority()
    }
}