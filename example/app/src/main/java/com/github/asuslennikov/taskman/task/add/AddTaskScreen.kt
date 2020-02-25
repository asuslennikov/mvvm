package com.github.asuslennikov.taskman.task.add

import android.app.DatePickerDialog
import androidx.navigation.fragment.findNavController
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.AddTaskBinding

class AddTaskScreen : Fragment<AddTaskState, AddTaskViewModel, AddTaskBinding>(
    R.layout.add_task,
    AddTaskViewModel::class.java
) {

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