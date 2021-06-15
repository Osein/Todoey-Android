package com.osein.todoey.data.viewmodels

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.osein.todoey.R
import com.osein.todoey.data.models.Priority

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    val prioritySpinnerListener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position) {
                0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red)) }
                1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow)) }
                2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green)) }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) { }
    }

    fun getPriorityFromSpinnerText(text: String): Priority {
        return when(text) {
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            else -> Priority.LOW
        }
    }

    fun getPrioritySpinnerIndex(priority: Priority): Int {
        return when(priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            else -> 2
        }
    }

    fun validateTodoInputData(title: String, description: String): Boolean {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(description))
    }

}