package com.github.asuslennikov.taskman.di

import com.github.asuslennikov.mvvm.api.presentation.ViewModel
import dagger.MapKey
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.reflect.KClass

@MapKey
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(SOURCE)
internal annotation class ViewModelKey(val value: KClass<out ViewModel<*>>)