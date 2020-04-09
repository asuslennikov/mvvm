package com.github.asuslennikov.taskman.task.add

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.ScreenAnimationUtils
import com.github.asuslennikov.taskman.databinding.AddTaskBinding

class AddTaskScreen : Fragment<AddTaskState, AddTaskViewModel, AddTaskBinding>(
    R.layout.add_task,
    AddTaskViewModel::class.java
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTaskSaveButton.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?, left: Int, top: Int, right: Int, bottom: Int,
                oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
            ) {
                v?.removeOnLayoutChangeListener(this)
                ScreenAnimationUtils.startCircularRevealAnimation(
                    ScreenAnimationUtils.AnimationData(
                        binding.addTaskSaveButton,
                        view,
                        R.color.raw_color_yellow_light,
                        android.R.color.background_light,
                        view.context.resources.getInteger(R.integer.common_circular_reveal_animation_duration).toLong()
                    )
                )
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = viewModel.onBackPressed()
        })
    }

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
        view?.apply {
            ScreenAnimationUtils.startCircularRevealAnimation(
                ScreenAnimationUtils.AnimationData(
                    binding.addTaskSaveButton,
                    this,
                    R.color.raw_color_yellow_light,
                    android.R.color.background_light,
                    context.resources.getInteger(R.integer.common_circular_reveal_animation_duration).toLong(),
                    true
                )
            ) {
                it.targetView.visibility = GONE
                findNavController().popBackStack()
            }
        }
    }
}