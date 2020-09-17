package com.example.todo.fragments.update

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.data.model.ToDoData
import com.example.todo.data.viewmodel.SharedViewModel
import com.example.todo.data.viewmodel.ToDoViewModel
import com.example.todo.databinding.FragmentUpdateBinding
import kotlinx.android.synthetic.main.fragment_update.*

class UpdateFragment : Fragment() {

    private val args: UpdateFragmentArgs by navArgs()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val binding: FragmentUpdateBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding?.currentPrioritiesSpinner?.onItemSelectedListener = sharedViewModel.listener
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> removeItem()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun removeItem() {
        val title = args.currentItem.title
        AlertDialog.Builder(requireContext())
            .apply {
                setTitle("Delete '$title'?")
                setMessage("Are you sure you want to delete '$title'?")
                setPositiveButton("YES") { _: DialogInterface, _: Int ->
                    toDoViewModel.delete(args.currentItem)
                    findNavController().navigate(R.id.action_updateFragment_to_listFragment)
                    Toast.makeText(requireContext(), "Deleted successfully", Toast.LENGTH_SHORT)
                        .show()
                }
                setNegativeButton("NO") { d, _ -> d.dismiss() }
                create()
                show()
            }
    }

    private fun updateItem() {
        val title = current_title_et.text.toString()
        val description = current_description_et.text.toString().trim()
        val priority = current_priorities_spinner.selectedItem.toString().trim()
        if (sharedViewModel.verifyData(title, description)) {
            ToDoData(
                args.currentItem.id,
                title,
                sharedViewModel.convertToPriority(priority),
                description
            ).let {
                toDoViewModel.updateData(it)
            }.also {
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
                Toast.makeText(
                    requireContext(),
                    "Updated successfully!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Fill in all fields",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}