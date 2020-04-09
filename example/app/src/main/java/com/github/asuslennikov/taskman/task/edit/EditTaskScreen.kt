package com.github.asuslennikov.taskman.task.edit

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.ScreenAnimationUtils
import com.github.asuslennikov.taskman.databinding.EditTaskBinding
import com.github.asuslennikov.taskman.task.onTextChange

class EditTaskScreen : Fragment<EditTaskState, EditTaskViewModel, EditTaskBinding>(
    R.layout.edit_task,
    EditTaskViewModel::class.java
) {
    private val screenArgs: EditTaskScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTaskSaveButton.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?, left: Int, top: Int, right: Int, bottom: Int,
                oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
            ) {
                v?.removeOnLayoutChangeListener(this)
                ScreenAnimationUtils.startCircularRevealAnimation(
                    ScreenAnimationUtils.AnimationData(
                        binding.editTaskSaveButton,
                        view,
                        R.color.raw_color_yellow_light,
                        android.R.color.background_light,
                        view.context.resources.getInteger(R.integer.common_circular_reveal_animation_duration).toLong()
                    )
                )
            }
        })
    }

    override fun onActivityCreated(saved: Bundle?) {
        super.onActivityCreated(saved)
        binding.editTaskTitle.onTextChange { viewModel.onTitleChanged(it) }
        binding.editTaskDescription.onTextChange { viewModel.onDescriptionChanged(it) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = viewModel.onBackPressed()
        })
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
        view?.apply {
            ScreenAnimationUtils.startCircularRevealAnimation(
                ScreenAnimationUtils.AnimationData(
                    binding.editTaskSaveButton,
                    this,
                    R.color.raw_color_yellow_light,
                    android.R.color.background_light,
                    context.resources.getInteger(R.integer.common_circular_reveal_animation_duration).toLong(),
                    true
                )
            ) {
                it.targetView.visibility = View.GONE
                findNavController().popBackStack()
            }
        }
    }
}