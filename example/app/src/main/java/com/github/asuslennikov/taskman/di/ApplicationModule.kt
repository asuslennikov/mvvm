package com.github.asuslennikov.taskman.di

import android.content.Context
import com.github.asuslennikov.mvvm.api.presentation.ViewModel
import com.github.asuslennikov.mvvm.presentation.ViewModelFactory
import com.github.asuslennikov.mvvm.presentation.ViewModelProvider
import com.github.asuslennikov.mvvm.presentation.di.AndroidXViewModelProvider
import com.github.asuslennikov.mvvm.presentation.di.InjectableViewModelFactory
import com.github.asuslennikov.taskman.RootViewModel
import com.github.asuslennikov.taskman.data.di.DataDependencies
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module(includes = [ApplicationModule.ViewModelBindingsModule::class])
class ApplicationModule(private val application: Context) : DataDependencies {

    @Provides
    override fun getContext(): Context {
        return application
    }

    @Provides
    @ApplicationScope
    fun viewModelFactory(instances: @JvmSuppressWildcards Map<Class<out ViewModel<*>>, Provider<ViewModel<*>>>): ViewModelFactory {
        return InjectableViewModelFactory(instances)
    }

    @Provides
    @ApplicationScope
    fun viewModelProvider(viewModelFactory: ViewModelFactory): ViewModelProvider {
        return AndroidXViewModelProvider(viewModelFactory)
    }

    @Module
    internal interface ViewModelBindingsModule {
/*
        @Binds
        @IntoMap
        @ViewModelKey(SplashViewModel::class)
        fun bindsSplashViewModel(instance: SplashViewModel): ViewModel<*>
 */

        @Binds
        @IntoMap
        @ViewModelKey(RootViewModel::class)
        fun bindsRootViewModel(instance: RootViewModel): ViewModel<*>
    }
}
