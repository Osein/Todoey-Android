package com.osein.todoey.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import com.osein.todoey.R
import com.osein.todoey.Utils
import com.osein.todoey.Utils.Companion.observeOnce
import com.osein.todoey.data.models.Todo
import com.osein.todoey.data.viewmodels.TodoViewModel
import com.osein.todoey.databinding.FragmentListBinding
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {
    private val viewModel: TodoViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.todoListRecyclerView.adapter = adapter
        binding.todoListRecyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        binding.todoListRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        setupSwipeToDelete(binding.todoListRecyclerView)
        viewModel.getTodos.observe(viewLifecycleOwner, {
            viewModel.checkIfEmpty(it)
            adapter.setTodoList(it)
        })
        setHasOptionsMenu(true)
        Utils.hideKeyboard(requireActivity())
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.todo_list_menu, menu)

        val searchView = menu.findItem(R.id.todoListMenuSearchItem).actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.todoListMenuSortItemPriorityHigh -> viewModel.getTodosSortedByHigh.observe(
                viewLifecycleOwner, {
                    adapter.setTodoList(it)
                }
            )
            R.id.todoListMenuSortItemPriorityLow -> viewModel.getTodosSortedByLow.observe(
                viewLifecycleOwner, {
                    adapter.setTodoList(it)
                }
            )
            R.id.todoListMenuDeleteAll -> deleteAllTodos()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchTodos(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null) {
            searchTodos(newText)
        }
        return true
    }

    private fun searchTodos(query: String) {
        viewModel.searchTodos("%$query%").observeOnce(this, {
            it.let {
                adapter.setTodoList(it)
            }
        })
    }

    private fun showRestoreSnack(view: View, deletedItem: Todo) {
        val snackbar = Snackbar.make(
            view, "Deleted: ${deletedItem.title}",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Undo") {
            viewModel.insertTodo(deletedItem)
        }
        snackbar.show()
    }

    private fun setupSwipeToDelete(recyclerView: RecyclerView) {
        (ItemTouchHelper(object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.todos[viewHolder.adapterPosition]
                viewModel.deleteTodo(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                showRestoreSnack(viewHolder.itemView, deletedItem)
            }
        })).attachToRecyclerView(recyclerView)
    }

    private fun deleteAllTodos() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAllTodos()
            Toast.makeText(requireContext(), "Successfully removed!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Are you sure?")
        builder.setTitle("This action will delete all todos!")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}