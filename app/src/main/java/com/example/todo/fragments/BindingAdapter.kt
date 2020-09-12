package com.example.todo.fragments

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.todo.R
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
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.GONE
            }
        }
    }

}