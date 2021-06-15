package com.osein.todoey.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.osein.todoey.R
import com.osein.todoey.data.models.Priority
import com.osein.todoey.data.models.Todo

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if(navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:visibleIfEmptyTodo")
        @JvmStatic
        fun visibleIfEmptyTodo(view: View, data: MutableLiveData<Boolean>) {
            when(data.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:priorityIndex")
        @JvmStatic
        fun priorityIndex(view: Spinner, data: Priority) {
            when(data) {
                Priority.HIGH -> view.setSelection(0)
                Priority.MEDIUM -> view.setSelection(1)
                Priority.LOW -> view.setSelection(2)
            }
        }

        @BindingAdapter("android:colorPriority")
        @JvmStatic
        fun colorPriority(view: CardView, data: Priority) {
            when(data) {
                Priority.HIGH -> view.setCardBackgroundColor(view.context.getColor(R.color.red))
                Priority.MEDIUM -> view.setCardBackgroundColor(view.context.getColor(R.color.yellow))
                Priority.LOW -> view.setCardBackgroundColor(view.context.getColor(R.color.green))
            }
        }

        @BindingAdapter("android:openUpdateFragmentWithTodo")
        @JvmStatic
        fun openUpdateFragmentWithTodo(view: ConstraintLayout, data: Todo) {
            view.setOnClickListener {
                view.findNavController().navigate(ListFragmentDirections.actionListFragmentToUpdateFragment(data))
            }
        }

    }

}