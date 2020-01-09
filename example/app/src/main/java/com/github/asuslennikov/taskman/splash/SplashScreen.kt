package com.github.asuslennikov.taskman.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.TaskApplication
import com.github.asuslennikov.taskman.databinding.SplashBinding

class SplashScreen : Fragment<SplashState, SplashViewModel, SplashBinding>(
    R.layout.splash,
    SplashViewModel::class.java
) {

    /**
     * It is splash screen, so application still can be in initialization state and therefore we can't use injection for this (and only this)
     * view model
     */
    override fun createViewModel(): SplashViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SplashViewModel(context?.applicationContext as TaskApplication) as T
            }
        }).get(SplashViewModel::class.java)
    }
}