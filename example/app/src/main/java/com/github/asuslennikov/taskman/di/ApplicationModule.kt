package com.github.asuslennikov.taskman.di

import android.content.Context
import com.github.asuslennikov.mvvm.api.presentation.ViewModel
import com.github.asuslennikov.taskman.RootState
import com.github.asuslennikov.taskman.RootViewModel
import com.github.asuslennikov.taskman.data.di.DataDependencies
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ApplicationModule.InnerModule::class])
class ApplicationModule(private val application: Context) : DataDependencies {

    @Provides
    override fun getContext(): Context {
        return application
    }

    @Module
    internal interface InnerModule {

        @Binds
        @ApplicationScope
        fun bindsRootViewModel(impl: RootViewModel): ViewModel<RootState>
    }
}
