package com.github.asuslennikov.taskman

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("android:visibility")
    fun bindImageDrawableResourceId(view: View, visibility: Boolean) {
        view.visibility = if (visibility) VISIBLE else GONE
    }

    @JvmStatic
    @BindingAdapter("onLongClick")
    fun bindViewLongClickListener(view: View?, listener: Runnable?) {
        view?.setOnLongClickListener listener@{
            listener?.run()
            return@listener true
        }
    }
}