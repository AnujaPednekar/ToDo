package com.example.todo.fragments.list

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.R
import com.example.todo.data.viewmodel.SharedViewModel
import com.example.todo.data.viewmodel.ToDoViewModel
import com.example.todo.databinding.FragmentListBinding

class ListFragment : Fragment() {

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
                    listAdapter.setData(data)
                }
            )
    }


    private fun setRecyclerview() {
        _binding?.recyclerView.apply {
            this?.adapter = listAdapter
            this?.layoutManager = LinearLayoutManager(requireActivity())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
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
}