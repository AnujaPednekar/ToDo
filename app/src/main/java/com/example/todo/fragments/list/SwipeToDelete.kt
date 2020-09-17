package com.example.todo.fragments.list

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


abstract class SwipeToDelete(dragDirs: Int = 0, swipeDirs: Int = ItemTouchHelper.LEFT) :
    ItemTouchHelper.SimpleCallback(
        dragDirs,
        swipeDirs
    ) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }
}