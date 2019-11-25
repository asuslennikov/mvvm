package com.github.asuslennikov.taskman

import android.app.Application
import com.github.asuslennikov.taskman.data.di.DaggerDataComponent
import com.github.asuslennikov.taskman.di.ApplicationComponent
import com.github.asuslennikov.taskman.di.ApplicationModule
import com.github.asuslennikov.taskman.di.DaggerApplicationComponent
import com.github.asuslennikov.taskman.domain.di.DaggerDomainComponent

class TaskApplication : Application() {
    private lateinit var appComponent: ApplicationComponent

    init {
        createComponentRegistry()
    }

    private fun createComponentRegistry() {
        val appModule = ApplicationModule(this)

        val dataProvider = DaggerDataComponent.builder()
            .dataDependencies(appModule)
            .build()

        appComponent = DaggerApplicationComponent.builder()
            .applicationModule(appModule)
            .dataProvider(dataProvider)
            .domainProvider(
                DaggerDomainComponent.builder()
                    .domainDependencies(dataProvider)
                    .build()
            )
            .build()

        appComponent.getRootViewModel()
    }
}