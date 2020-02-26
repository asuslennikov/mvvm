package com.github.asuslennikov.taskman.task.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.AddTaskBinding

class AddTaskScreen : Fragment<AddTaskState, AddTaskViewModel, AddTaskBinding>(
    R.layout.add_task,
    AddTaskViewModel::class.java
) {

    override fun onActivityCreated(saved: Bundle?) {
        super.onActivityCreated(saved)
        binding.addTaskTitle.onTextChange { viewModel.onTitleChanged(it) }
        binding.addTaskDescription.onTextChange { viewModel.onDescriptionChanged(it) }
    }

    fun EditText.onTextChange(listener: (String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listener.invoke(s?.toString() ?: "")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }
        })
    }

    override fun applyEffect(screenEffect: Effect) {
        super.applyEffect(screenEffect)
        when (screenEffect) {
            is OpenDateChooserEffect -> showDatePicker()
            is CloseAddTaskScreenEffect -> closeScreen()
        }
    }

    private fun showDatePicker() {
        savedState?.run {
            context?.let { context ->
                DatePickerDialog(
                    context,
                    { _, y, m, d -> viewModel.onDateSelected(y, m, d) },
                    date.year,
                    date.monthValue - 1,
                    date.dayOfMonth
                ).show()
            }
        }
    }

    private fun closeScreen() {
        findNavController().popBackStack()
    }
}