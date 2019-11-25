package com.github.asuslennikov.taskman.di

import com.github.asuslennikov.taskman.ComponentRegistry
import com.github.asuslennikov.taskman.data.di.DataProvider
import com.github.asuslennikov.taskman.domain.di.DomainProvider
import dagger.Component

@ApplicationScope
@Component(
    modules = [ApplicationModule::class],
    dependencies = [DomainProvider::class, DataProvider::class]
)
interface ApplicationComponent : ComponentRegistry {


}