package com.osein.todoey.data.converters

import androidx.room.TypeConverter
import com.osein.todoey.data.models.Priority

class PriorityConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}