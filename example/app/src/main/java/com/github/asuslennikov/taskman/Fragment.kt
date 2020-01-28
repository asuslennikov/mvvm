package com.github.asuslennikov.taskman

import androidx.databinding.ViewDataBinding
import com.github.asuslennikov.mvvm.api.presentation.State
import com.github.asuslennikov.mvvm.api.presentation.ViewModel
import com.github.asuslennikov.mvvm.presentation.BoundFragmentScreen
import com.github.asuslennikov.mvvm.presentation.ViewModelProvider

abstract class Fragment<STATE : State, VM : ViewModel<STATE>, B : ViewDataBinding>(
    private val layoutId: Int,
    private val viewModelClass: Class<VM>
) : BoundFragmentScreen<STATE, VM, B>() {

    override fun getLayoutResourceId(): Int = layoutId

    override fun getBindingViewModelVariableId(): Int = BR.viewModel

    override fun getBindingStateVariableId(): Int = BR.state

    protected fun getViewModelProvider(): ViewModelProvider {
        return ((context?.applicationContext as? ComponentRegistry)?.getViewModelProvider()
            ?: throw RuntimeException("Can't retrieve a ViewModel provider"))
    }

    override fun createViewModel(): VM {
        val viewModelProvider = getViewModelProvider()
        return viewModelProvider.getViewModel(this, viewModelClass)
    }

}