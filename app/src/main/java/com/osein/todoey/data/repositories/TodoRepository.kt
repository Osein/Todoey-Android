package com.osein.todoey.data.repositories

import androidx.lifecycle.LiveData
import com.osein.todoey.data.daos.TodoDao
import com.osein.todoey.data.models.Todo

class TodoRepository(
    private val todoDao: TodoDao
) {

    fun getAllTodos(): LiveData<List<Todo>> = todoDao.getAllTodos()
    fun getAllTodosSortedByHigh(): LiveData<List<Todo>> = todoDao.sortByHighPriority()
    fun getAllTodosSortedByLow(): LiveData<List<Todo>> = todoDao.sortByLowPriority()

    suspend fun addTodo(todo: Todo) = todoDao.addTodo(todo)

    suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo)

    suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)

    suspend fun deleteAllTodos() = todoDao.deleteAll()

    fun searchTodos(query: String) = todoDao.searchTodos(query)

}