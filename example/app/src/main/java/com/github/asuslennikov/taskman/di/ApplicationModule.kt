package com.github.asuslennikov.taskman.di

import android.content.Context
import com.github.asuslennikov.mvvm.api.presentation.ViewModel
import com.github.asuslennikov.mvvm.presentation.ViewModelFactory
import com.github.asuslennikov.mvvm.presentation.ViewModelProvider
import com.github.asuslennikov.mvvm.presentation.di.AndroidXViewModelProvider
import com.github.asuslennikov.mvvm.presentation.di.InjectableViewModelFactory
import com.github.asuslennikov.taskman.data.di.DataDependencies
import com.github.asuslennikov.taskman.task.add.AddTaskViewModel
import com.github.asuslennikov.taskman.task.card.TaskCardViewModel
import com.github.asuslennikov.taskman.task.edit.EditTaskViewModel
import com.github.asuslennikov.taskman.task.list.TasksListViewModel
import com.github.asuslennikov.taskman.task.list.item.DateHeaderViewModel
import com.github.asuslennikov.taskman.task.list.item.TaskItemViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.threeten.bp.Clock
import javax.inject.Provider

@Module(includes = [ApplicationModule.ViewModelBindingsModule::class])
class ApplicationModule(private val application: Context) : DataDependencies {

    @Provides
    @ApplicationScope
    override fun getContext(): Context {
        return application
    }

    @Provides
    @ApplicationScope
    fun viewModelFactory(instances: @JvmSuppressWildcards Map<Class<out ViewModel<*>>, Provider<ViewModel<*>>>): ViewModelFactory {
        return InjectableViewModelFactory(instances).apply {
            instances.keys.forEach { clazz -> create(clazz) } // load dependency graph for each view model class
        }
    }

    @Provides
    @ApplicationScope
    fun viewModelProvider(viewModelFactory: ViewModelFactory): ViewModelProvider {
        return AndroidXViewModelProvider(viewModelFactory)
    }

    @Provides
    @ApplicationScope
    fun clock() = Clock.systemDefaultZone()

    @Module
    internal interface ViewModelBindingsModule {
/*
        @Binds
        @IntoMap
        @ViewModelKey(::class)
        fun binds(instance: ): ViewModel<*>
 */

        @Binds
        @IntoMap
        @ViewModelKey(TasksListViewModel::class)
        fun bindsTasksListViewModel(instance: TasksListViewModel): ViewModel<*>

        @Binds
        @IntoMap
        @ViewModelKey(DateHeaderViewModel::class)
        fun bindsDateHeaderViewModel(instance: DateHeaderViewModel): ViewModel<*>

        @Binds
        @IntoMap
        @ViewModelKey(TaskItemViewModel::class)
        fun bindsTaskItemViewModel(instance: TaskItemViewModel): ViewModel<*>

        @Binds
        @IntoMap
        @ViewModelKey(TaskCardViewModel::class)
        fun bindsTaskCardViewModel(instance: TaskCardViewModel): ViewModel<*>

        @Binds
        @IntoMap
        @ViewModelKey(AddTaskViewModel::class)
        fun bindsAddTaskViewModel(instance: AddTaskViewModel): ViewModel<*>

        @Binds
        @IntoMap
        @ViewModelKey(EditTaskViewModel::class)
        fun bindsEditTaskViewModel(instance: EditTaskViewModel): ViewModel<*>
    }
}
