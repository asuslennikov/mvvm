package com.github.asuslennikov.taskman

import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.mvvm.api.presentation.Screen
import com.github.asuslennikov.mvvm.api.presentation.ViewModel
import com.github.asuslennikov.taskman.domain.task.GetTaskUseCase
import io.reactivex.Observable
import javax.inject.Inject

class RootViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase
) : ViewModel<RootState> {

    override fun getState(screen: Screen<RootState>): Observable<RootState> {
        return Observable.error<RootState>(RuntimeException("not implemented"));
    }

    override fun getEffect(screen: Screen<RootState>): Observable<Effect> {
        return Observable.error<Effect>(RuntimeException("not implemented"));
    }
}
