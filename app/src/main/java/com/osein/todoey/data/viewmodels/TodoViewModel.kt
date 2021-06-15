package com.osein.todoey.data.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.osein.todoey.data.TodoDatabase
import com.osein.todoey.data.models.Todo
import com.osein.todoey.data.repositories.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): BaseViewModel(application) {

    private val todoDao = TodoDatabase.getDatabase(application).todoDao()
    private val repository: TodoRepository = TodoRepository(todoDao)

    val getTodos: LiveData<List<Todo>> = repository.getAllTodos()
    val getTodosSortedByHigh: LiveData<List<Todo>> = repository.getAllTodosSortedByHigh()
    val getTodosSortedByLow: LiveData<List<Todo>> = repository.getAllTodosSortedByLow()
    val isEmpty: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfEmpty(todos: List<Todo>) {
        isEmpty.value = todos.isEmpty()
    }

    fun insertTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

    fun deleteAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTodos()
        }
    }

    fun searchTodos(query: String) = repository.searchTodos(query)

}