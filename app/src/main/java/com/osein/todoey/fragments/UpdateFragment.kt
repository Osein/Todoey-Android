package com.osein.todoey.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.osein.todoey.R
import com.osein.todoey.data.models.Todo
import com.osein.todoey.data.viewmodels.TodoViewModel
import com.osein.todoey.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private val todoViewModel: TodoViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args
        binding.updateTodoPriority.onItemSelectedListener = todoViewModel.prioritySpinnerListener
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_todo_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.updateTodoMenuSave -> saveTodo()
            R.id.updateTodoMenuDelete -> deleteTodo()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveTodo() {
        val title = binding.updateTodoTitle.text.toString()
        val description = binding.updateTodoDescription.text.toString()
        val priority = todoViewModel.getPriorityFromSpinnerText(binding.updateTodoPriority.selectedItem.toString())

        if(todoViewModel.validateTodoInputData(title, description)) {
            todoViewModel.updateTodo(Todo(
                args.todo.id,
                title,
                priority,
                description
            ))
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Empty data!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteTodo() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            todoViewModel.deleteTodo(args.todo)
            Toast.makeText(requireContext(), "Successfully removed: ${args.todo.title}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Are you sure?")
        builder.setTitle("This action will delete the todo(${args.todo.title})")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}