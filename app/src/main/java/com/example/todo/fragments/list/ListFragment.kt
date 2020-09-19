package com.example.todo.fragments.list

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.model.ToDoData
import com.example.todo.data.viewmodel.SharedViewModel
import com.example.todo.data.viewmodel.ToDoViewModel
import com.example.todo.databinding.FragmentListBinding
import com.example.todo.fragments.list.adapter.TodoAdapter
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val listAdapter: TodoAdapter by lazy { TodoAdapter() }
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = this
        _binding?.mSharedViewModel = sharedViewModel
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setRecyclerview()
        observeLiveData()
    }

    private fun observeLiveData() {
        toDoViewModel.getAllData()
            .observe(
                viewLifecycleOwner,
                { data ->
                    sharedViewModel.updateDbStatus(data)
                    listAdapter.setData(data)
                }
            )
    }

    private fun setRecyclerview() {
        _binding?.recyclerView?.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            swipeToDelete(this)
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val callback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = listAdapter.todoList[position]
                toDoViewModel.delete(item)
                listAdapter.notifyItemRemoved(position)
                restore(viewHolder.itemView, position, item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restore(view: View, position: Int, item: ToDoData) {
        Snackbar.make(
            view,
            "Deleted '${item.title}'",
            Snackbar.LENGTH_SHORT
        ).setAction("Undo") {
            toDoViewModel.insertData(item)
            listAdapter.notifyItemChanged(position)
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(this@ListFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            removeAllItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun removeAllItem() {
        AlertDialog.Builder(requireContext())
            .apply {
                setTitle("Delete everything?")
                setMessage("Are you sure you want to delete everything?")
                setPositiveButton("YES") { _: DialogInterface, _: Int ->
                    toDoViewModel.deleteAll()
                    Toast.makeText(
                        requireContext(),
                        "Everything is deleted!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                setNegativeButton("NO") { d, _ -> d.dismiss() }
                create()
                show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        performSearchOperationUsing(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        performSearchOperationUsing(query)
        return true
    }

    private fun performSearchOperationUsing(query: String?) {
        val title = "%$query%"
        query?.let {
            toDoViewModel.search(title).observe(
                this,
                { list ->
                    list?.let {
                        listAdapter.setData(list)
                    }
                }
            )
        }
    }
}