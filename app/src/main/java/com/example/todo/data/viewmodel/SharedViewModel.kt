package com.example.todo.data.viewmodel

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todo.R
import com.example.todo.data.model.Priority
import com.example.todo.data.model.ToDoData

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    var isDataAvailableLiveData: MutableLiveData<Boolean> = MutableLiveData(true)

    fun verifyData(title: String, description: String): Boolean {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(description))
    }

    fun convertToPriority(priority: String): Priority {
        return when (priority) {
            "HIGH" -> Priority.HIGH
            "MEDIUM" -> Priority.MEDIUM
            "LOW" -> Priority.LOW
            else -> Priority.LOW
        }
    }

    fun updateDbStatus(list: List<ToDoData>) {
        isDataAvailableLiveData.value = list.isNotEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                0 -> (parent?.getChildAt(0) as TextView).setTextColor(
                    ContextCompat.getColor(
                        application,
                        R.color.red
                    )
                )
                1 -> (parent?.getChildAt(0) as TextView).setTextColor(
                    ContextCompat.getColor(
                        application,
                        R.color.yellow
                    )
                )
                2 -> (parent?.getChildAt(0) as TextView).setTextColor(
                    ContextCompat.getColor(
                        application,
                        R.color.green
                    )
                )
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }
}