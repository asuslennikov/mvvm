package com.github.asuslennikov.taskman.data.di

import dagger.Component

@DataScope
@Component(
    modules = [DataModule::class],
    dependencies = [DataDependencies::class]
)
internal interface DataComponent : DataProvider {
}