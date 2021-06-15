package com.osein.todoey.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.osein.todoey.data.models.Todo
import com.osein.todoey.databinding.TodoListRowBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    var todos = emptyList<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    fun setTodoList(newTodos: List<Todo>) {
        val toDoDiffUtil = ToDoDiffUtil(todos, newTodos)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.todos = newTodos
        toDoDiffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = todos.count()

    class ViewHolder(private val binding: TodoListRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    TodoListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }
}