package com.github.asuslennikov.taskman.task.edit

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.EditTaskBinding
import com.github.asuslennikov.taskman.task.onTextChange

class EditTaskScreen : Fragment<EditTaskState, EditTaskViewModel, EditTaskBinding>(
    R.layout.edit_task,
    EditTaskViewModel::class.java
) {
    private val screenArgs: EditTaskScreenArgs by navArgs()

    override fun onActivityCreated(saved: Bundle?) {
        super.onActivityCreated(saved)
        binding.editTaskTitle.onTextChange { viewModel.onTitleChanged(it) }
        binding.editTaskDescription.onTextChange { viewModel.onDescriptionChanged(it) }
    }

    override fun getSavedState(): EditTaskState? =
        super.getSavedState() ?: EditTaskState.loading(screenArgs.taskId)

    override fun applyEffect(screenEffect: Effect) {
        super.applyEffect(screenEffect)
        when (screenEffect) {
            is OpenDateChooserEffect -> showDatePicker()
            is CloseEditTaskScreenEffect -> closeScreen()
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