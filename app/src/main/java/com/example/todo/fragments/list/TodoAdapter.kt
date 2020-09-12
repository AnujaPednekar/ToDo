package com.example.todo.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.model.Priority
import com.example.todo.data.model.ToDoData
import com.example.todo.fragments.list.TodoAdapter.MyViewHolder
import kotlinx.android.synthetic.main.row_item.view.*

class TodoAdapter : RecyclerView.Adapter<MyViewHolder>() {

    var todoList = emptyList<ToDoData>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item,
            parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = todoList[position]

        holder.itemView.apply {
            title_tv.text = currentItem.title
            description_tv.text = currentItem.description
            row_main.setOnClickListener {
                ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                    .let { findNavController().navigate(it) }
            }

            when (currentItem.priority) {
                Priority.HIGH -> priority_indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.red
                    )
                )
                Priority.MEDIUM -> priority_indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.yellow
                    )
                )
                Priority.LOW -> priority_indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.green
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setData(todoList: List<ToDoData>) {
        this.todoList = todoList
        notifyDataSetChanged()
    }
}