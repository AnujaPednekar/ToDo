package com.example.todo.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.data.model.ToDoData
import com.example.todo.data.viewmodel.SharedViewModel
import com.example.todo.data.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private val todoViewModel:ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        priorities_spinner.onItemSelectedListener = sharedViewModel.listener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val title = title_et.text.toString()
        val priority = priorities_spinner.selectedItem.toString()
        val description = description_et.text.toString()
        if (sharedViewModel.verifyData(title, description)) {
            val todoData = ToDoData(
                id = 0,
                title,
                sharedViewModel.convertToPriority(priority),
                description
            )
            todoViewModel.insertData(todoData)
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
            Toast.makeText(requireContext(), "Todo added!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Fill out all the fields.", Toast.LENGTH_SHORT).show()
        }
    }
}


