package com.github.asuslennikov.taskman.splash

import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.TaskApplication
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class SplashViewModel(private val app: TaskApplication) : AbstractViewModel<SplashState>() {
    private val disposable = CompositeDisposable()

    init {
        disposable.add(Observable.combineLatest(
            applicationLoading(),
            minimalLoading(),
            BiFunction { l1: TaskApplication.InitializationStatus, _: Long -> l1 }
        ).subscribe { onLoadingCompleted() })
    }

    private fun minimalLoading(): Observable<Long> {
        return Observable.timer(app.resources.getInteger(R.integer.splash_visibility_minimum_duration).toLong(), TimeUnit.MILLISECONDS)
    }

    private fun applicationLoading(): Observable<TaskApplication.InitializationStatus> =
        app.getInitializationStatus().filter { TaskApplication.InitializationStatus.COMPLETE == it }

    private fun onLoadingCompleted() {
        sendState(SplashState(false))
        sendEffect(SplashEffect.OPEN_TASKS_LIST_SCREEN)
    }

    override fun buildInitialState(): SplashState = SplashState(true)

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}