package com.example.todo.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.model.ToDoData
import com.example.todo.databinding.RowItemBinding
import com.example.todo.fragments.list.adapter.TodoAdapter.MyViewHolder

class TodoAdapter : RecyclerView.Adapter<MyViewHolder>() {

    var todoList = emptyList<ToDoData>()

    class MyViewHolder(private val binding: RowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(toDoData: ToDoData) {
            binding.todoData = toDoData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RowItemBinding.inflate(inflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setData(todoList: List<ToDoData>) {
        this.todoList = todoList
        notifyDataSetChanged()
    }
}