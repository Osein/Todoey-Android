package com.osein.todoey.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.osein.todoey.data.daos.TodoDao
import com.osein.todoey.data.models.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var instance: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase = instance
            ?: synchronized(this) {
                buildDatabase(context)
            }

        private fun buildDatabase(context: Context): TodoDatabase = Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }

}