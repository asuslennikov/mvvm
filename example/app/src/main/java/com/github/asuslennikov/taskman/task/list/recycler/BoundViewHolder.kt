package com.github.asuslennikov.taskman.task.list.recycler

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.mvvm.api.presentation.Screen
import com.github.asuslennikov.mvvm.api.presentation.ViewModel
import com.github.asuslennikov.mvvm.presentation.BR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

abstract class BoundViewHolder<STATE : ListItemState, VM : ViewModel<STATE>, B : ViewDataBinding>(
    itemView: View,
    private val viewModel: VM
) : ViewHolder(itemView), Screen<STATE> {

    protected companion object {
        const val NO_ACTUAL_ID = 0
    }

    protected val binding: B = DataBindingUtil.bind(itemView)!!
    private val disposable: CompositeDisposable = CompositeDisposable()
    private var screenState: STATE? = null

    init {
        binding.setVariable(getBindingViewModelVariableId(), viewModel)
        val screenVariableId = getBindingScreenVariableId()
        if (screenVariableId != NO_ACTUAL_ID) {
            binding.setVariable(screenVariableId, this)
        }
        itemView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) {
                disposable.clear()
            }

            override fun onViewAttachedToWindow(v: View?) {
                // do nothing
            }
        })
    }

    open fun bind(state: STATE) {
        disposable.clear()
        screenState = state
        disposable.add(viewModel.getState(this)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { render(it) })
        if (screenSupportsEffects()) {
            disposable.addAll(viewModel.getEffect(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { applyEffect(it) })
        }
    }

    /**
     * It provides a data-binding id for state variable (check the `variable` value in
     * `data` section of layout file). It will be used for [ViewDataBinding.setVariable(int, Object)].
     * This variable is not mandatory and can be skipped in your layout.
     *
     * @return an id for state variable
     * @see Screen.render
     */
    protected open fun getBindingStateVariableId() = BR.state

    /**
     * It provides a data-binding id for view model variable (check the `variable` value in
     * `data` section of layout file). It will be used for [ViewDataBinding.setVariable(int, Object)].
     * This variable is not mandatory and can be skipped in your layout.
     *
     * @return an id for view model variable
     */
    protected open fun getBindingViewModelVariableId() = BR.viewModel

    /**
     * It provides a data-binding id for screen variable (check the `variable` value in
     * `data` section of layout file). It will be used for [ViewDataBinding.setVariable(int, Object)].
     * This variable is not mandatory and can be skipped in your layout.
     *
     * @return an id for screen variable
     */
    protected open fun getBindingScreenVariableId(): Int {
        return NO_ACTUAL_ID
    }

    /**
     * Method defines view holder ability to process effect. If it returns `false`, then subscription to effects will not be created.
     * As a result we can save some resources (memory, cpu)
     *
     * @return `true` if holder can process effects
     */
    protected open fun screenSupportsEffects(): Boolean = false

    override fun getSavedState(): STATE? {
        return screenState
    }

    override fun render(screenState: STATE) {
        binding.setVariable(getBindingStateVariableId(), screenState)
    }

    override fun applyEffect(screenEffect: Effect) {
        // do nothing by default
    }
}