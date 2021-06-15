package com.osein.todoey.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.osein.todoey.R
import com.osein.todoey.data.models.Todo
import com.osein.todoey.data.viewmodels.TodoViewModel
import com.osein.todoey.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private val viewModel: TodoViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        binding.addNewTodoPriority.onItemSelectedListener = viewModel.prioritySpinnerListener
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_todo_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addTodoMenuAddItem) {
            insertTodo()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertTodo() {
        val title = binding.addNewTodoTitle.text.toString()
        val priority = viewModel.getPriorityFromSpinnerText(binding.addNewTodoPriority.selectedItem.toString())
        val description = binding.addNewTodoDescription.text.toString()

        if(viewModel.validateTodoInputData(title, description)) {
            viewModel.insertTodo(Todo(0, title, priority, description))
            Toast.makeText(context, "Todo added successfully.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Invalid input!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}