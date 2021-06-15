package com.osein.todoey.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.osein.todoey.data.converters.PriorityConverter
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todos")
@TypeConverters(PriorityConverter::class)
@Parcelize
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
): Parcelable
