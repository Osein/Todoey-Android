package com.osein.todoey.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.osein.todoey.data.models.Todo

@Dao
interface TodoDao {

    @Query("SELECT * FROM todos ORDER BY id ASC")
    fun getAllTodos(): LiveData<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTodo(todoData: Todo)

    @Update
    suspend fun updateTodo(todoData: Todo)

    @Delete
    suspend fun deleteTodo(data: Todo)

    @Query("DELETE FROM todos")
    suspend fun deleteAll()

    @Query("SELECT * FROM todos WHERE title LIKE :searchQuery")
    fun searchTodos(searchQuery: String): LiveData<List<Todo>>

    @Query("SELECT * FROM todos ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<Todo>>

}