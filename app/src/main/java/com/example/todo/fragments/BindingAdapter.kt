package com.example.todo.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.todo.R
import com.example.todo.data.model.Priority
import com.example.todo.data.model.ToDoData
import com.example.todo.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapter {


    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:isDataAvailable")
        @JvmStatic
        fun isDataAvailable(view: View, isDataAvailable: MutableLiveData<Boolean>) {
            when (isDataAvailable.value) {
                true -> view.visibility = View.GONE
                false -> view.visibility = View.VISIBLE
            }
        }

        @BindingAdapter("android:setPriority")
        @JvmStatic
        fun setPriority(spinner: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> spinner.setSelection(0)
                Priority.MEDIUM -> spinner.setSelection(1)
                Priority.LOW -> spinner.setSelection(2)
            }
        }

        @BindingAdapter("android:setPriorityIndicator")
        @JvmStatic
        fun setPriorityIndicator(cardView: CardView, priority: Priority) {
            when (priority) {
                Priority.HIGH -> cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        cardView.context,
                        R.color.red
                    )
                )
                Priority.MEDIUM -> cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        cardView.context,
                        R.color.yellow
                    )
                )
                Priority.LOW -> cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        cardView.context,
                        R.color.green
                    )
                )
            }
        }

        @BindingAdapter("android:navigateToUpdateFragment")
        @JvmStatic
        fun navigateToUpdateFragment(constraintLayout: ConstraintLayout, toDoData: ToDoData) {
            constraintLayout.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(toDoData)
                constraintLayout.findNavController().navigate(action)
            }
        }

    }

}