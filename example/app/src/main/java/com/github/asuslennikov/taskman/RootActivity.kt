package com.github.asuslennikov.taskman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.mvvm.api.presentation.Screen

class RootActivity : AppCompatActivity(), Screen<RootState> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.root)

        val taskApplication = application as TaskApplication
        taskApplication.getInitializationStatus()
            .filter { status -> status == TaskApplication.InitializationStatus.COMPLETE }
            .subscribe {
                val viewModel = taskApplication.getViewModelProvider()
                    .getViewModel(this, RootViewModel::class.java)
                println(viewModel)
            }

    }

    override fun applyEffect(screenEffect: Effect) {
        // do nothing
    }

    override fun render(screenState: RootState) {
        // do nothing
    }

    override fun getSavedState(): RootState? {
        return null
    }
}
